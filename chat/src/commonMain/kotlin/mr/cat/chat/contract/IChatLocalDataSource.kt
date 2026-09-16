package mr.cat.chat.contract

import kotlinx.coroutines.flow.Flow
import mr.cat.chat.model.ChatMessage

interface IChatLocalDataSource {
    suspend fun saveMessage(message: ChatMessage)
    fun getMessages(threadId: String): Flow<List<ChatMessage>>
    suspend fun updateThreadInfo(threadId: String, metadata: Map<String, Any>)
    suspend fun getDailyMessageCount(): Int
    suspend fun incrementMessageCount()
}
