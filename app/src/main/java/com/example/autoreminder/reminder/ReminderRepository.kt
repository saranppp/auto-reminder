package com.example.autoreminder.reminder

import com.example.autoreminder.data.ReminderDao
import com.example.autoreminder.data.ReminderEventChecker
import com.example.autoreminder.data.toDomain
import com.example.autoreminder.data.toEntity
import com.example.autoreminder.domain.Reminder
import com.example.autoreminder.domain.ReminderStatus
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ReminderRepository(
    private val dao: ReminderDao,
    private val eventChecker: ReminderEventChecker = ReminderEventChecker()
) {
    val reminders: Flow<List<Reminder>> = dao.observeAll().map { entities ->
        entities.map { it.toDomain() }
    }

    suspend fun add(reminder: Reminder) {
        dao.insert(reminder.toEntity())
    }

    suspend fun updateStatus(id: String, status: ReminderStatus) {
        dao.updateStatus(id, status.name)
    }

    fun evaluateReminder(reminder: Reminder, eventSignal: String): Boolean {
        return eventChecker.shouldTrigger(reminder, eventSignal)
    }
}
