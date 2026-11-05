package com.example.habitsapp.authentication.presentation.signup

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.habitsapp.authentication.domain.usecase.PasswordResult
import com.example.habitsapp.authentication.domain.usecase.SignupUseCase
import com.example.habitsapp.authentication.domain.usecase.ValidateEmailUseCase
import com.example.habitsapp.authentication.domain.usecase.ValidatePasswordUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignupViewModel @Inject constructor(
    private val validateEmailUseCase: ValidateEmailUseCase,
    private val validatePasswordUseCase: ValidatePasswordUseCase,
    private val signupUseCase: SignupUseCase,
) : ViewModel() {
    var state by mutableStateOf(SignupState())
        private set

    fun onEvent(event: SignupEvent) {
        when (event) {
            is SignupEvent.EmailChanged -> {
                state = state.copy(email = event.email, emailError = null, passwordError = null)
            }

            is SignupEvent.PasswordChanged -> {
                state =
                    state.copy(password = event.password, emailError = null, passwordError = null)
            }

            SignupEvent.LogIn -> {
                state = state.copy(logIn = true)
            }

            SignupEvent.SignUp -> {
                signUp()
            }
        }
    }

    private fun signUp() {
        state = state.copy(isLoading = true)
        val passwordResult = validatePasswordUseCase(state.password)
        when {
            !validateEmailUseCase(state.email) -> {
                state = state.copy(emailError = "Invalid email", isLoading = false)
            }

            passwordResult is PasswordResult.Invalid -> {
                state = state.copy(passwordError = passwordResult.message, isLoading = false)
            }

            else -> {
                viewModelScope.launch {
                    signupUseCase(state.email, state.password)
                        .onSuccess {
                            println("Success")
                            state = state.copy(isSignedIn = true, isLoading = false)
                        }
                        .onFailure {
                            state = state.copy(emailError = it.message, isLoading = false)
                        }
                }
            }
        }
    }
}