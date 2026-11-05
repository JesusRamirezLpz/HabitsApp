package com.example.habitsapp.home.data.mapper

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.habitsapp.home.data.extensions.toStartOfDayTimeStamp
import com.example.habitsapp.home.data.extensions.toTimeStamp
import com.example.habitsapp.home.data.extensions.toZoneDateTime
import com.example.habitsapp.home.data.extensions.toZonedDateTime
import com.example.habitsapp.home.data.local.entity.HabitEntity
import com.example.habitsapp.home.data.local.entity.HabitSyncEntity
import com.example.habitsapp.home.data.remote.dto.HabitDto
import com.example.habitsapp.home.data.remote.dto.HabitResponse
import com.example.habitsapp.home.domain.models.Habit
import java.time.DayOfWeek
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
fun HabitEntity.toDomain(): Habit = Habit(
    id = this.id,
    name = this.name,
    frequency = this.frequency.map { DayOfWeek.of(it) },
    completedDays = this.completedDays.map { it.toZoneDateTime().toLocalDate() },
    reminder = this.reminder.toZoneDateTime().toLocalTime(),
    startDate = this.startDate.toZoneDateTime(),
)

@RequiresApi(Build.VERSION_CODES.O)
fun Habit.toEntity(): HabitEntity = HabitEntity(
    id = this.id,
    name = this.name,
    frequency = this.frequency.map { it.value },
    completedDays = this.completedDays.map { it.toZonedDateTime().toTimeStamp() },
    reminder = this.reminder.toZoneDateTime().toTimeStamp(),
    startDate = this.startDate.toStartOfDayTimeStamp()
)

@RequiresApi(Build.VERSION_CODES.O)
fun HabitResponse.toDomain(): List<Habit> = this.entries.map {
    with(it) {
        Habit(
            id = key,
            name = value.name,
            frequency = value.frequency.map { DayOfWeek.of(it) },
            completedDays = value.completedDays?.map { it.toZoneDateTime().toLocalDate() }
                ?: emptyList(),
            reminder = value.reminder.toZoneDateTime().toLocalTime(),
            startDate = value.startDate.toZoneDateTime(),
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
fun Habit.toDto(): HabitResponse = mapOf(
    this.id to HabitDto(
        name = this.name,
        frequency = this.frequency.map { it.value },
        completedDays = this.completedDays.map { it.toZonedDateTime().toTimeStamp() },
        reminder = this.reminder.toZoneDateTime().toTimeStamp(),
        startDate = this.startDate.toStartOfDayTimeStamp()
    )
)

fun Habit.toHabitSyncEntity() = HabitSyncEntity(this.id)

