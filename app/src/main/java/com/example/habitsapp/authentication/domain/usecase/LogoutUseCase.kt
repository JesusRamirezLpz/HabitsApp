package com.example.habitsapp.authentication.domain.usecase

import com.example.habitsapp.authentication.domain.repository.AuthenticationRepository

class LogoutUseCase(private val repository: AuthenticationRepository) {
    suspend operator fun invoke() {
        repository.logout()
    }
}