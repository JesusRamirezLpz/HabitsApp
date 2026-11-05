package com.example.habitsapp.home.domain.models

import java.time.DayOfWeek
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZonedDateTime

data class Habit(
    val name: String,
    val frequency: List<DayOfWeek>,
    val completedDays: List<LocalDate> = emptyList(),
    val reminder: LocalTime,
    val id: String,
    val startDate: ZonedDateTime
)