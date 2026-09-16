package mr.cat.chat.engine

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import mr.cat.chat.contract.IChatLocalDataSource
import mr.cat.chat.contract.IChatRemoteDataSource
import mr.cat.chat.model.*
import kotlin.math.pow
import kotlin.time.Duration.Companion.milliseconds

class ChatEngine(
    private val remoteDataSource: IChatRemoteDataSource,
    private val localDataSource: IChatLocalDataSource,
    private val config: ChatConfig = ChatConfig(),
    private val coroutineScope: CoroutineScope
) {
    private val _uiState = MutableStateFlow(ChatUiState())
    val uiState: StateFlow<ChatUiState> = _uiState.asStateFlow()

    private var timerJob: Job? = null
    private var currentActiveThreadId: String? = null

    suspend fun sendMessage(text: String, threadId: String?, context: String? = null) {
        currentActiveThreadId = threadId
        
        // 1. Validasi kuota harian
        val currentCount = localDataSource.getDailyMessageCount()
        if (currentCount >= config.dailyLimit) {
            _uiState.update { it.copy(status = ChatStatus.ERROR, error = ChatError.LimitReached) }
            return
        }

        val actualThreadId = threadId ?: "default"

        // 2. Optimistic insert pesan user
        val userMessage = ChatMessage(
            id = generateId(),
            threadId = actualThreadId,
            text = text,
            role = ChatRole.USER
        )
        localDataSource.saveMessage(userMessage)
        
        _uiState.update { state ->
            state.copy(
                messages = state.messages + userMessage,
                status = ChatStatus.SENDING,
                error = null,
                loadingSeconds = 0
            )
        }

        startTimer()

        // 3. Fetch remote dengan retry policy
        var retryCount = 0
        var result: Result<ChatMessage>? = null
        
        while (retryCount <= config.maxRetries) {
            try {
                result = remoteDataSource.fetchResponse(text, actualThreadId, context)
                if (result.isSuccess) break
            } catch (e: Exception) {
                result = Result.failure(e)
            }
            
            retryCount++
            if (retryCount <= config.maxRetries) {
                val backoff = (config.retryBackoffMultiplier.pow(retryCount.toDouble()) * 1000).toLong()
                delay(backoff.milliseconds)
            }
        }

        stopTimer()

        // 4. Validasi threadId masih aktif sebelum menyimpan respons
        // Mencegah race condition jika user pindah thread saat fetch berjalan
        if (currentActiveThreadId != threadId) return

        // 5. Finalisasi
        if (result?.isSuccess == true) {
            val botMessage = result.getOrThrow()
            localDataSource.saveMessage(botMessage)
            localDataSource.incrementMessageCount()
            
            _uiState.update { state ->
                state.copy(
                    messages = state.messages + botMessage,
                    status = ChatStatus.SUCCESS
                )
            }
        } else {
            val errorMsg = result?.exceptionOrNull()?.message ?: "Network error"
            _uiState.update { state ->
                state.copy(
                    status = ChatStatus.ERROR,
                    error = ChatError.NetworkError(errorMsg)
                )
            }
        }
    }

    private fun startTimer() {
        timerJob?.cancel()
        timerJob = coroutineScope.launch {
            while (true) {
                delay(1000.milliseconds)
                _uiState.update { it.copy(loadingSeconds = it.loadingSeconds + 1) }
            }
        }
    }

    private fun stopTimer() {
        timerJob?.cancel()
        timerJob = null
    }

    private fun generateId(): String = kotlin.time.Clock.System.now().toEpochMilliseconds().toString()
}
