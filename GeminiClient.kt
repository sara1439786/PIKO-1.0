package com.example.api

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Safe on-device fallback. No AI key is stored in the APK.
 * A server-side AI endpoint can replace this later without exposing credentials.
 */
object GeminiClient {
    suspend fun analyzeSentiment(transcript: String, customApiKey: String? = null): String = withContext(Dispatchers.Default) {
        val text = transcript.lowercase()
        val positive = listOf("interested", "yes", "book", "appointment", "good", "great", "send details", "price", "visit", "confirm")
        val negative = listOf("not interested", "don't call", "do not call", "wrong number", "stop", "expensive", "no thanks", "remove")
        val p = positive.count { text.contains(it) }
        val n = negative.count { text.contains(it) }
        when {
            n > p -> "Negative"
            p > n -> "Positive"
            else -> "Neutral"
        }
    }
}
