package com.example.habitsapp.home.presentation.homemain

import com.example.habitsapp.home.domain.models.Habit
import java.time.ZonedDateTime

sealed interface HomeEvent {
    data class OnChangeDate(val date: ZonedDateTime) : HomeEvent
    data class OnCompleteHabit(val habit: Habit) : HomeEvent
}