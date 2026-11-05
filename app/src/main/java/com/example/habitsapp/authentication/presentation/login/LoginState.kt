package com.example.habitsapp.authentication.presentation.login

data class LoginState(
    val email: String = "",
    val password: String = "",
    val mailError: String? = null,
    val passwordError: String? = null,
    val isLoggedIn: Boolean = false,
    val isLoading: Boolean = false
)
