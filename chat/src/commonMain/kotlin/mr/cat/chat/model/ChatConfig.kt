package mr.cat.chat.model

data class ChatConfig(
    val dailyLimit: Int = 100,
    val maxRetries: Int = 4,
    val timeoutSeconds: Int = 60,
    val retryBackoffMultiplier: Double = 2.0
)
