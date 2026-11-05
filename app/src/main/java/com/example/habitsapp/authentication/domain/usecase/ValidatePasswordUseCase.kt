package com.example.habitsapp.authentication.domain.usecase

import com.example.habitsapp.authentication.domain.usecase.PasswordResult.Invalid
import com.example.habitsapp.authentication.domain.usecase.PasswordResult.Valid

class ValidatePasswordUseCase {
    operator fun invoke(password: String): PasswordResult =
        when {
            (password.length < 6) ->
                Invalid("Password must be at least 6 characters long")

            (!password.any { it.isUpperCase() }) ->
                Invalid("Password must contain at least one uppercase letter")

            (!password.any { it.isLowerCase() }) ->
                Invalid("Password must contain at least one lowercase letter")

            (!password.any { it.isDigit() }) ->
                Invalid("Password must contain at least one digit")

            else -> Valid
        }
}

sealed class PasswordResult() {
    object Valid : PasswordResult()
    data class Invalid(val message: String) : PasswordResult()
}