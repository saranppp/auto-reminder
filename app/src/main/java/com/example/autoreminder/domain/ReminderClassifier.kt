package com.example.autoreminder.domain

data class ReminderDraft(
    val cleanedText: String,
    val triggerType: ReminderType,
    val eventName: String?
)

class ReminderClassifier {
    fun classify(input: String): ReminderDraft {
        val cleaned = input.trim()
        if (cleaned.isEmpty()) {
            return ReminderDraft("", ReminderType.OTHER, null)
        }

        val lower = cleaned.lowercase()

        val movieKeywords = listOf("movie", "film", "cinema", "trailer")
        val productKeywords = listOf("product", "restock", "in stock", "available", "sale")

        val eventName = when {
            lower.contains("movie") || lower.contains("film") -> extractNamedEntity(cleaned)
            lower.contains("product") || lower.contains("restock") -> extractNamedEntity(cleaned)
            else -> null
        }

        return when {
            movieKeywords.any { lower.contains(it) } -> {
                ReminderDraft(cleaned, ReminderType.MOVIE_RELEASE, eventName)
            }
            productKeywords.any { lower.contains(it) } -> {
                ReminderDraft(cleaned, ReminderType.PRODUCT_RESTOCK, eventName)
            }
            else -> ReminderDraft(cleaned, ReminderType.CUSTOM_EVENT, eventName)
        }
    }

    private fun extractNamedEntity(text: String): String? {
        val token = text
            .replace(Regex("(?i)remind me to|notify me when|remind me|when|the|about|for|book tickets|for the movie|for the product"), "")
            .trim()
            .replace(Regex("\\s+"), " ")

        return token.ifBlank { null }
    }
}
