package com.example.autoreminder.worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.autoreminder.data.ReminderDatabase
import com.example.autoreminder.data.toDomain
import com.example.autoreminder.domain.ReminderStatus
import com.example.autoreminder.data.ReminderEventChecker
import kotlinx.coroutines.flow.first

class ReminderCheckWorker(
    appContext: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result {
        val database = ReminderDatabase.getDatabase(applicationContext)
        val reminders = database.reminderDao().observeAll().first()
        val checker = ReminderEventChecker()

        for (entity in reminders) {
            val reminder = entity.toDomain()
            if (reminder.status == ReminderStatus.WAITING && checker.shouldTrigger(reminder, "released")) {
                database.reminderDao().updateStatus(reminder.id, ReminderStatus.TRIGGERED.name)
            }
        }

        return Result.success()
    }
}
