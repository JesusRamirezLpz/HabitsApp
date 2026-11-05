package com.example.habitsapp.home.presentation.detail

import java.time.DayOfWeek
import java.time.LocalTime

sealed class DetailEvent {
    data class OnHabitNameChange(val habitName: String) : DetailEvent()
    data class OnFrequencyChange(val dayOfWeek: DayOfWeek) : DetailEvent()
    data class OnReminderChange(val time: LocalTime) : DetailEvent()
    object OnSaveHabit : DetailEvent()
}