package com.example.autoreminder.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.autoreminder.domain.Reminder
import com.example.autoreminder.domain.ReminderClassifier
import com.example.autoreminder.domain.ReminderStatus
import com.example.autoreminder.reminder.ReminderRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class ReminderUiState(
    val input: String = "",
    val isSubmitting: Boolean = false,
    val detectedType: String = "movie_release"
)

class ReminderViewModel : ViewModel() {
    private val repository = ReminderRepository()
    private val classifier = ReminderClassifier()

    private val _uiState = MutableStateFlow(ReminderUiState())
    val uiState: StateFlow<ReminderUiState> = _uiState.asStateFlow()

    val reminders = repository.reminders

    fun onInputChange(input: String) {
        _uiState.value = _uiState.value.copy(input = input)
        val draft = classifier.classify(input)
        _uiState.value = _uiState.value.copy(
            detectedType = draft.triggerType.name.lowercase()
        )
    }

    fun createReminder() {
        val userText = _uiState.value.input.trim()
        if (userText.isEmpty()) return

        val draft = classifier.classify(userText)

        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isSubmitting = true)

            repository.add(
                Reminder(
                    userText = userText,
                    triggerType = draft.triggerType,
                    eventName = draft.eventName ?: draft.cleanedText,
                    status = ReminderStatus.WAITING
                )
            )

            _uiState.value = _uiState.value.copy(
                input = "",
                isSubmitting = false,
                detectedType = "movie_release"
            )
        }
    }
}
