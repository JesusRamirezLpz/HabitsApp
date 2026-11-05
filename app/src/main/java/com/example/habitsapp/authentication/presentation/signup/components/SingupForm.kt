package com.example.habitsapp.authentication.presentation.signup.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.example.habitsapp.authentication.presentation.signup.SignupEvent
import com.example.habitsapp.authentication.presentation.signup.SignupState
import com.example.habitsapp.core.presentation.HabitPasswordTextfield
import com.example.habitsapp.core.presentation.HabitTextfield
import com.example.habitsapp.core.presentation.HabitsButton

@Composable
fun SignupForm(
    modifier: Modifier,
    state: SignupState,
    onEvent: (SignupEvent) -> Unit,
) {
    Column(modifier = modifier) {
        HabitTextfield(
            value = state.email,
            onValueChange = { onEvent(SignupEvent.EmailChanged(it)) },
            placeholder = "Email",
            contentDescription = "Email",
            leadingIcon = Icons.Outlined.Email,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            backgroundColor = Color.White,
            errorMessage = state.emailError,
            isEnabled = !state.isLoading,
        )
        Spacer(modifier = Modifier.height(16.dp))
        HabitPasswordTextfield(
            value = state.password,
            onValueChange = { onEvent(SignupEvent.PasswordChanged(it)) },
            placeholder = "Password",
            contentDescription = "Password",
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            backgroundColor = Color.White,
            errorMessage = state.passwordError,
            isEnabled = !state.isLoading,
        )
        Spacer(modifier = Modifier.height(16.dp))
        HabitsButton(
            "Create Account",
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            isLoading = state.isLoading,
            onClick = { onEvent(SignupEvent.SignUp) }
        )
        Spacer(modifier = Modifier.height(16.dp))
        TextButton(
            onClick = { onEvent(SignupEvent.LogIn) },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text(
                text = buildAnnotatedString {
                    append("Already have an account? ")
                    withStyle(style = MaterialTheme.typography.bodyLarge.toSpanStyle()) {
                        append("Sign In")
                    }
                },
                color = MaterialTheme.colorScheme.tertiary,
            )
        }
    }
}