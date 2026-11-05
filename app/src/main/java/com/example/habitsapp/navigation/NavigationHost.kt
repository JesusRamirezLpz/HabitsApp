package com.example.habitsapp.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.habitsapp.authentication.presentation.login.LoginScreen
import com.example.habitsapp.authentication.presentation.signup.SignupScreen
import com.example.habitsapp.home.presentation.detail.DetailScreen
import com.example.habitsapp.home.presentation.homemain.HomeScreen
import com.example.habitsapp.onboarding.presentation.OnboardingScreen
import com.example.habitsapp.settings.presentation.SettingsScreen

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NavigationHost(startDestination: Any, logout: () -> Unit) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = startDestination) {
        composable<Onboarding> {
            OnboardingScreen(onFinish = {
                navController.navigate(Login) {
                    popUpTo<Login> { inclusive = true }
                }
            })
        }

        composable<Login> {
            LoginScreen(onSignUp = {
                navController.navigate(SignUpScreen) {
                    popUpTo<Login> {
                        inclusive = true
                    }
                }
            }) {
                navController.navigate(HomeScreen) {
                    popUpTo<Login> { inclusive = true }
                }
            }
        }

        composable<HomeScreen> {
            HomeScreen(
                navigateToDetails = { navController.navigate(SettingsScreen) },
                onEditHabit = {
                    navController.navigate(DetailScreen(it))
                }
            )
        }

        composable<SignUpScreen> {
            SignupScreen(
                onLogIn = {
                    navController.popBackStack()
                }) {
                navController.navigate(HomeScreen) {
                    popUpTo(navController.graph.id) { inclusive = true }
                }
            }
        }

        composable<DetailScreen> { navBackStackEntry ->
            val detail = navBackStackEntry.toRoute<DetailScreen>()
            DetailScreen(
                id = detail.id,
                onBackPressed = { navController.popBackStack() },
                onSaved = { navController.popBackStack() })
        }

        composable<SettingsScreen> {
            SettingsScreen(
                onLogout = {
                    logout()
                    navController.navigate(Login) {
                        popUpTo(navController.graph.id) { inclusive = true }
                    }
                },
                onBack = { navController.popBackStack() }
            )
        }
    }
}