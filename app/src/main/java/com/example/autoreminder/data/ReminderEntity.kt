package com.example.autoreminder.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.autoreminder.domain.Reminder
import com.example.autoreminder.domain.ReminderStatus
import com.example.autoreminder.domain.ReminderType
import java.util.UUID

@Entity(tableName = "reminders")
data class ReminderEntity(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    @ColumnInfo(name = "user_text") val userText: String,
    @ColumnInfo(name = "trigger_type") val triggerType: String,
    @ColumnInfo(name = "event_name") val eventName: String?,
    @ColumnInfo(name = "status") val status: String,
    @ColumnInfo(name = "created_at") val createdAt: Long = System.currentTimeMillis()
)

fun ReminderEntity.toDomain(): Reminder = Reminder(
    id = id,
    userText = userText,
    triggerType = ReminderType.valueOf(triggerType),
    eventName = eventName,
    status = ReminderStatus.valueOf(status),
    createdAt = createdAt
)

fun Reminder.toEntity(): ReminderEntity = ReminderEntity(
    id = id,
    userText = userText,
    triggerType = triggerType.name,
    eventName = eventName,
    status = status.name,
    createdAt = createdAt
)
