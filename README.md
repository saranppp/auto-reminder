# Auto Reminder

A reminder application designed for event-based triggers instead of only fixed time alarms.

## Goal

This app is built around reminders such as:

- "Remind me to book tickets when the movie is released"
- "Notify me when this product is back in stock"
- "Remind me when this event happens"

The app classifies the reminder, stores it, and is designed to monitor external event conditions instead of only time-based notifications.

## Current implementation status

This repository contains the initial Android project scaffold and a Compose-based reminder UI for event-based reminder creation.

## Planned architecture

- Kotlin + Jetpack Compose UI
- ViewModel for reminder input and detection
- Reminder domain model and classifier
- Repository layer for reminder storage
- WorkManager background polling for event monitoring
- Notification delivery when an event matches

## Next steps

1. Add Room persistence
2. Add background task scheduling
3. Add event monitoring for movie-release and product-restock triggers
4. Add notification sending when the condition is met
