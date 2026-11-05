package com.example.habitsapp.authentication.presentation.signup

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.habitsapp.R
import com.example.habitsapp.authentication.presentation.signup.components.SignupForm
import com.example.habitsapp.core.presentation.HabitsTitle

@Composable
fun SignupScreen(
    modifier: Modifier = Modifier,
    viewModel: SignupViewModel = hiltViewModel(),
    onLogIn: () -> Unit,
    onSignIn: () -> Unit
) {
    val state = viewModel.state
    LaunchedEffect(key1 = state.isSignedIn) {
        if (state.isSignedIn) {
            onSignIn()
        }
    }

    LaunchedEffect(key1 = state.logIn) {
        if (state.logIn) {
            onLogIn()
        }
    }

    Scaffold { innerPadding ->
        Box(modifier = modifier.padding(innerPadding)) {
            Column {
                Spacer(modifier = Modifier.weight(1f))
                Image(
                    painter = painterResource(id = R.drawable.signup_image),
                    contentDescription = "background",
                    modifier = Modifier
                        .fillMaxWidth()
                        .size(150.dp),
                )
                Spacer(modifier = Modifier.weight(1f))
                HabitsTitle("Create your Account", modifier.fillMaxWidth())
                Spacer(modifier = Modifier.weight(1f))
                SignupForm(modifier.fillMaxWidth(), state, viewModel::onEvent)
                Spacer(modifier = Modifier.weight(1f))
            }
        }

    }
}
