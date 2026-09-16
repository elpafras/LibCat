package mr.cat.libcat.demo

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import mr.cat.chat.contract.IChatLocalDataSource
import mr.cat.chat.model.ChatMessage

class DemoChatLocalDataSource : IChatLocalDataSource {
    private val messages = MutableStateFlow<List<ChatMessage>>(emptyList())
    private var dailyCount = 0
    private val threadMetadata = mutableMapOf<String, Map<String, Any>>()

    override suspend fun saveMessage(message: ChatMessage) {
        messages.value = messages.value + message
    }

    override fun getMessages(threadId: String): Flow<List<ChatMessage>> {
        return messages.map { list -> list.filter { it.threadId == threadId } }
    }

    override suspend fun updateThreadInfo(threadId: String, metadata: Map<String, Any>) {
        threadMetadata[threadId] = metadata
    }

    override suspend fun getDailyMessageCount(): Int {
        return dailyCount
    }

    override suspend fun incrementMessageCount() {
        dailyCount++
    }
}
