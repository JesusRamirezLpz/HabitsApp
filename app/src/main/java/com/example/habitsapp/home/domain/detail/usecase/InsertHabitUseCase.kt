package com.example.habitsapp.home.domain.detail.usecase

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.habitsapp.home.domain.models.Habit
import com.example.habitsapp.home.domain.repository.HomeRepository

@RequiresApi(Build.VERSION_CODES.O)
class InsertHabitUseCase(private val repository: HomeRepository) {
    suspend operator fun invoke(habit: Habit) {
        repository.insertHabit(habit)
    }
}