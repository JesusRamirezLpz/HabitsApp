package com.example.habitsapp.navigation

import kotlinx.serialization.Serializable

@Serializable
object Onboarding

@Serializable
object Login

@Serializable
object HomeScreen

@Serializable
object SignUpScreen

@Serializable
data class DetailScreen(val id: String?)

@Serializable
object SettingsScreen