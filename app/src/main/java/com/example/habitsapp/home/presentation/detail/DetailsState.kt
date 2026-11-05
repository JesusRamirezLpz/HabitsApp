package com.example.habitsapp.home.presentation.detail

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZonedDateTime

@RequiresApi(Build.VERSION_CODES.O)
data class DetailsState(
    val id: String? = null,
    val habitName: String = "",
    val frequency: List<DayOfWeek> = emptyList(),
    val reminder: LocalTime = LocalTime.now(),
    val completedDates: List<LocalDate> = emptyList(),
    val startDate: ZonedDateTime = ZonedDateTime.now(),
    val isSaved: Boolean = false
)