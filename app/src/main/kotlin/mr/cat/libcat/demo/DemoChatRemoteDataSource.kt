package mr.cat.libcat.demo

import kotlinx.coroutines.delay
import mr.cat.chat.contract.IChatRemoteDataSource
import mr.cat.chat.model.ChatMessage
import mr.cat.chat.model.ChatRole
import kotlin.random.Random

class DemoChatRemoteDataSource : IChatRemoteDataSource {
    private val responses = listOf(
        "Halo! Ada yang bisa saya bantu?",
        "Itu pertanyaan yang menarik.",
        "Saya sedang memikirkan jawabannya...",
        "Bisa tolong jelaskan lebih detail?",
        "Menarik sekali! Mari kita bahas lebih lanjut."
    )

    override suspend fun fetchResponse(
        message: String,
        threadId: String?,
        context: String?
    ): Result<ChatMessage> {
        // Simulasi network latency 1-3 detik
        delay(Random.nextLong(1000, 3000))

        // Simulasi kegagalan ~20% untuk demo retry policy
        if (Random.nextFloat() < 0.2f) {
            return Result.failure(Exception("Simulated network failure"))
        }

        val responseText = if (Random.nextBoolean()) {
            "Kamu bilang: $message"
        } else {
            responses.random()
        }

        val chatMessage = ChatMessage(
            id = Random.nextInt().toString(),
            threadId = threadId ?: "demo-thread",
            text = responseText,
            role = ChatRole.BOT
        )

        return Result.success(chatMessage)
    }
}
