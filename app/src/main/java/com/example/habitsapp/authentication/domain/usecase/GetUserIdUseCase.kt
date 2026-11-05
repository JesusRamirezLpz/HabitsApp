package com.example.habitsapp.authentication.domain.usecase

import com.example.habitsapp.authentication.domain.repository.AuthenticationRepository

class GetUserIdUseCase(private val repository: AuthenticationRepository) {
    operator fun invoke(): String? = repository.getUserId()
}