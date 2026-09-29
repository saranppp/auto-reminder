package com.example.autoreminder.reminder

import com.example.autoreminder.domain.Reminder
import com.example.autoreminder.domain.ReminderStatus
import com.example.autoreminder.domain.ReminderType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class ReminderRepository {
    private val _reminders = MutableStateFlow(
        listOf(
            Reminder(
                id = "demo-1",
                userText = "Remind me to book tickets when the movie is released",
                triggerType = ReminderType.MOVIE_RELEASE,
                eventName = "New movie release",
                status = ReminderStatus.WAITING
            )
        )
    )

    val reminders: StateFlow<List<Reminder>> = _reminders.asStateFlow()

    fun add(reminder: Reminder) {
        _reminders.value = listOf(reminder) + _reminders.value
    }

    fun updateStatus(id: String, status: ReminderStatus) {
        _reminders.value = _reminders.value.map { reminder ->
            if (reminder.id == id) reminder.copy(status = status) else reminder
        }
    }
}
