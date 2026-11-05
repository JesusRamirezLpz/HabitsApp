package com.example.habitsapp.authentication.domain.usecase

import com.example.habitsapp.authentication.domain.repository.AuthenticationRepository

class SignupUseCase(private val repository: AuthenticationRepository) {
    suspend operator fun invoke(email: String, password: String) =
        repository.signup(email, password)
}