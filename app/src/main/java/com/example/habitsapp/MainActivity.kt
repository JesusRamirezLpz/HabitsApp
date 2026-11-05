package com.example.habitsapp

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import com.example.habitsapp.navigation.HomeScreen
import com.example.habitsapp.navigation.Login
import com.example.habitsapp.navigation.NavigationHost
import com.example.habitsapp.navigation.Onboarding
import com.example.habitsapp.ui.theme.HabitsAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    val viewModel: MainViewModel by viewModels()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            HabitsAppTheme {
                NavigationHost(startDestination = getStartedScreen()) {
                    viewModel.logout()
                }
            }
        }
    }

    private fun getStartedScreen() = when {
        viewModel.isLoggedIn -> HomeScreen

        viewModel.hasSeenOnboarding -> Login

        else -> Onboarding
    }
}