package com.example.habitsapp.authentication.domain.usecase

import com.example.habitsapp.authentication.domain.repository.AuthenticationRepository

class LoginUseCase(private val repository: AuthenticationRepository) {
    suspend operator fun invoke(email: String, password: String) =
        repository.login(email, password)
}