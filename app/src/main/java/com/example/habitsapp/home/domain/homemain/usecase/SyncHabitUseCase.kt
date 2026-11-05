package com.example.habitsapp.home.domain.homemain.usecase

import com.example.habitsapp.home.domain.repository.HomeRepository

class SyncHabitUseCase(private val repository: HomeRepository) {
    suspend operator fun invoke() {
       repository.syncHabits()
    }
}