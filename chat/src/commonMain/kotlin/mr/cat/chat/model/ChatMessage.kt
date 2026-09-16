package mr.cat.chat.model

data class ChatMessage(
    val id: String,
    val threadId: String,
    val text: String,
    val role: ChatRole,
    val timestamp: kotlin.time.Instant = kotlin.time.Clock.System.now(),
    val metadata: Map<String, Any>? = null
)
