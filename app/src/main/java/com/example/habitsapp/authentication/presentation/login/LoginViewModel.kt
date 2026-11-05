package com.example.habitsapp.authentication.presentation.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.habitsapp.authentication.domain.usecase.LoginUseCase
import com.example.habitsapp.authentication.domain.usecase.PasswordResult
import com.example.habitsapp.authentication.domain.usecase.ValidateEmailUseCase
import com.example.habitsapp.authentication.domain.usecase.ValidatePasswordUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val validateEmailUseCase: ValidateEmailUseCase,
    private val validatePasswordUseCase: ValidatePasswordUseCase,
    private val loginUseCase: LoginUseCase,
) : ViewModel() {
    var state by mutableStateOf(LoginState())
        private set

    fun onEvent(event: LoginEvent) {
        when (event) {
            is LoginEvent.EmailChanged -> state =
                state.copy(email = event.email, mailError = null, passwordError = null)

            is LoginEvent.PasswordChanged -> state =
                state.copy(password = event.password, mailError = null, passwordError = null)

            LoginEvent.Login -> login()

        }

    }

    private fun login() {
        state = state.copy(isLoading = true)
        val passwordResult = validatePasswordUseCase(state.password)
        when {
            !validateEmailUseCase(state.email) -> {
                state = state.copy(mailError = "Invalid email", isLoading = false)
            }

            passwordResult is PasswordResult.Invalid -> {
                state = state.copy(passwordError = passwordResult.message, isLoading = false)
            }

            else -> {
                viewModelScope.launch {
                    loginUseCase(state.email, state.password)
                        .onSuccess {
                            println("Success")
                            state = state.copy(isLoggedIn = true, isLoading = false)
                        }
                        .onFailure {
                            val error =
                                if (it.message?.contains("INVALID_LOGIN_CREDENTIALS") == true) {
                                    "Invalid email or password"
                                } else {
                                    it.message
                                }
                            state = state.copy(passwordError = error, isLoading = false)
                        }
                }
            }
        }
    }
}