package com.example.habitsapp.authentication.domain.usecase

import com.example.habitsapp.authentication.domain.matcher.EmailMatcher

class ValidateEmailUseCase(private val emailMatcher: EmailMatcher) {
    operator fun invoke(email: String) = emailMatcher.isValidEmail(email)
}