package com.example.habitsapp.home.presentation.homemain

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.habitsapp.home.domain.models.Habit
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZonedDateTime


@RequiresApi(Build.VERSION_CODES.O)
data class HomeState (
    val currentDate: ZonedDateTime = ZonedDateTime.now(),
    val selectedDate: ZonedDateTime = ZonedDateTime.now(),
    val habits: List<Habit> = emptyList()
)