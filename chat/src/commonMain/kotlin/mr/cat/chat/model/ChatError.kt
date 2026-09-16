package mr.cat.chat.model

sealed class ChatError {
    object LimitReached : ChatError()
    data class NetworkError(val message: String) : ChatError()
    data class GenericError(val message: String) : ChatError()
}
