package com.example.habitsapp.onboarding.presentation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.habitsapp.R
import com.example.habitsapp.onboarding.presentation.components.OnboardingPager

@Composable
fun OnboardingScreen(
    onboardingViewModel: OnboardingViewModel = hiltViewModel(),
    onFinish: () -> Unit = {}
) {
    LaunchedEffect(key1 = onboardingViewModel.hasSeenOnboarding) {
        if (onboardingViewModel.hasSeenOnboarding) {
            onFinish()
        }
    }
    Scaffold { innerPadding ->
        val pages = listOf(
            OnboardingPagerInfo(
                title = "Welcome to Monumental Habits",
                subtitle = "We can help you to be a better version of yourself.",
                image = R.drawable.welcome_img
            ),
            OnboardingPagerInfo(
                title = "Create new habits easily",
                subtitle = "We can help you to be a better version of yourself.",
                image = R.drawable.create_habits_img
            ),
            OnboardingPagerInfo(
                title = "Keep track of your progress",
                subtitle = "We can help you to be a better version of yourself.",
                image = R.drawable.keep_track_img
            ),
            OnboardingPagerInfo(
                title = "Join a supportive community",
                subtitle = "We can help you to be a better version of yourself.",
                image = R.drawable.join_img
            )
        )
        OnboardingPager(pages = pages, modifier = Modifier.padding(innerPadding)) {
            onboardingViewModel.completeOnboarding()
        }
    }
}