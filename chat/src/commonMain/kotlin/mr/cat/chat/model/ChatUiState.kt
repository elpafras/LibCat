package mr.cat.chat.model

import mr.cat.chat.model.ChatMessage

data class ChatUiState(
    val messages: List<ChatMessage> = emptyList(),
    val status: ChatStatus = ChatStatus.IDLE,
    val loadingSeconds: Int = 0,
    val error: ChatError? = null
)
