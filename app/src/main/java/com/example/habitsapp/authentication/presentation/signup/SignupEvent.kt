package com.example.habitsapp.authentication.presentation.signup

sealed interface SignupEvent{
    data class EmailChanged(val email: String) : SignupEvent
    data class PasswordChanged(val password: String) : SignupEvent
    object LogIn : SignupEvent
    object SignUp : SignupEvent
}
