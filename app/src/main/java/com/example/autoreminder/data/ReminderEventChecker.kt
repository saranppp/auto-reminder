package com.example.autoreminder.data

import com.example.autoreminder.domain.Reminder
import com.example.autoreminder.domain.ReminderStatus
import com.example.autoreminder.domain.ReminderType

class ReminderEventChecker {
    fun shouldTrigger(reminder: Reminder, eventSignal: String): Boolean {
        val lower = eventSignal.lowercase()
        val userText = reminder.userText.lowercase()

        return when (reminder.triggerType) {
            ReminderType.MOVIE_RELEASE -> lower.contains("released") ||
                (reminder.eventName != null && lower.contains(reminder.eventName!!.lowercase())) ||
                userText.contains("movie") && lower.contains("release")

            ReminderType.PRODUCT_RESTOCK -> lower.contains("restocked") ||
                lower.contains("available") ||
                lower.contains("in stock")

            ReminderType.CUSTOM_EVENT -> lower.contains("announced") ||
                lower.contains("updated") ||
                lower.contains("happened") ||
                userText.contains("when")

            ReminderType.OTHER -> false
        }
    }

    fun markTriggeredIfNeeded(reminder: Reminder, eventSignal: String): Reminder {
        return if (shouldTrigger(reminder, eventSignal)) {
            reminder.copy(status = ReminderStatus.TRIGGERED)
        } else {
            reminder
        }
    }
}
