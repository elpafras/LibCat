package mr.cat.chat.contract

import mr.cat.chat.model.ChatMessage

interface IChatRemoteDataSource {
    suspend fun fetchResponse(
        message: String,
        threadId: String?,
        context: String? = null
    ): Result<ChatMessage>
}
