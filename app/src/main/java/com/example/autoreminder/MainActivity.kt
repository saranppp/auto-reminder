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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.example.autoreminder.ui.theme.AutoReminderTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
fun ReminderScreen() {
    var reminderText by remember { mutableStateOf(TextFieldValue("")) }
    var eventType by remember { mutableStateOf("movie_release") }
    val reminders = remember {
        mutableStateListOf(
            ReminderItem(
                id = 1,
                text = "Remind me to book tickets when the movie is released",
                eventType = "movie_release",
                status = "waiting"
            )
        )
    }

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

        OutlinedTextField(
            value = reminderText,
            onValueChange = { reminderText = it },
            label = { Text("What should be reminded?") },
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = {
                if (reminderText.text.isNotBlank()) {
                    reminders.add(
                        ReminderItem(
                            id = System.currentTimeMillis().toInt(),
                            text = reminderText.text,
                            eventType = eventType,
                            status = "waiting"
                        )
                    )
                    reminderText = TextFieldValue("")
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Create reminder")
        }

        Text(
            text = "Event type: $eventType",
            style = MaterialTheme.typography.bodyMedium
        )

        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            items(reminders) { item ->
                ReminderCard(item)
            }
        }
    }
}

@Composable
fun ReminderCard(reminder: ReminderItem) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(reminder.text)
            Text("Type: ${reminder.eventType}")
            Text("Status: ${reminder.status}")
        }
    }
}

data class ReminderItem(
    val id: Int,
    val text: String,
    val eventType: String,
    val status: String
)
