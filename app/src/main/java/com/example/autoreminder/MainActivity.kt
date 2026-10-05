package com.example.autoreminder

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.autoreminder.domain.Reminder
import com.example.autoreminder.ui.ReminderViewModel
import com.example.autoreminder.ui.theme.AutoReminderTheme
import com.example.autoreminder.worker.ReminderScheduler

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ReminderScheduler(this).schedule()

        setContent {
            AutoReminderTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    ReminderScreen()
                }
            }
        }
    }
}

@Composable
fun ReminderScreen(viewModel: ReminderViewModel = viewModel()) {
    val uiState by viewModel.uiState.collectAsState()
    val reminders by viewModel.reminders.collectAsState(initial = emptyList())

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Auto Reminder",
            style = MaterialTheme.typography.headlineMedium
        )

        Text(
            text = "Detected trigger: ${uiState.detectedType}",
            style = MaterialTheme.typography.bodyLarge
        )

        OutlinedTextField(
            value = uiState.input,
            onValueChange = viewModel::onInputChange,
            label = { Text("What should trigger the reminder?") },
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = viewModel::createReminder,
            modifier = Modifier.fillMaxWidth(),
            enabled = uiState.input.isNotBlank() && !uiState.isSubmitting
        ) {
            Text("Create reminder")
        }

        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            items(reminders) { reminder ->
                ReminderCard(reminder)
            }
        }
    }
}

@Composable
fun ReminderCard(reminder: Reminder) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(reminder.userText)
            Text("Type: ${reminder.triggerType.name}")
            Text("Event: ${reminder.eventName ?: "Custom event"}")
            Text("Status: ${reminder.status.name}")
        }
    }
}
