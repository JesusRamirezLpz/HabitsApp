package com.example.habitsapp.authentication.presentation.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.habitsapp.R
import com.example.habitsapp.authentication.presentation.login.components.LoginForm
import com.example.habitsapp.core.presentation.HabitsTitle

//@Preview(showBackground = true)
@Composable
fun LoginScreen(
    viewModel: LoginViewModel = hiltViewModel(),
    onSignUp: () -> Unit,
    onLogin: () -> Unit
) {
    val state = viewModel.state

    LaunchedEffect(key1 = state.isLoggedIn) {
        if (state.isLoggedIn) {
            onLogin()
        }
    }

    Scaffold { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            Image(
                painter = painterResource(id = R.drawable.loginbackground),
                contentDescription = "background",
                modifier = Modifier.fillMaxWidth(),
                contentScale = ContentScale.Crop
            )
            Spacer(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                MaterialTheme.colorScheme.background,
                                MaterialTheme.colorScheme.background,
                            )
                        )
                    )
            )
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Spacer(modifier = Modifier)
                Spacer(modifier = Modifier)
                HabitsTitle("Welcome to Monumental Habits",
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(horizontal = 32.dp)
                )
                LoginForm(
                    state = state,
                    onEvent = viewModel::onEvent,
                    onSignUp = onSignUp,
                )
            }
        }
    }
}