package com.example.habitsapp.home.domain.homemain.usecase

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.habitsapp.home.domain.models.Habit
import com.example.habitsapp.home.domain.repository.HomeRepository
import java.time.ZonedDateTime

@RequiresApi(Build.VERSION_CODES.O)
class CompleteHabitUseCase(
    private val repository: HomeRepository
) {
    suspend operator fun invoke(habit: Habit, date: ZonedDateTime) {
        val newHabit = if (habit.completedDays.contains(date.toLocalDate())) {
            habit.copy(completedDays = habit.completedDays - date.toLocalDate())
        } else {
            habit.copy(completedDays = habit.completedDays + date.toLocalDate())
        }
        repository.insertHabit(newHabit)
    }
}