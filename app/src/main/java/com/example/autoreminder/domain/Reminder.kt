package com.example.autoreminder.domain

import java.util.UUID

enum class ReminderType {
    MOVIE_RELEASE,
    PRODUCT_RESTOCK,
    CUSTOM_EVENT,
    OTHER
}

data class Reminder(
    val id: String = UUID.randomUUID().toString(),
    val userText: String,
    val triggerType: ReminderType,
    val eventName: String? = null,
    val notes: String = "",
    val status: ReminderStatus = ReminderStatus.WAITING,
    val createdAt: Long = System.currentTimeMillis()
)

enum class ReminderStatus {
    WAITING,
    TRIGGERED,
    DISMISSED
}
