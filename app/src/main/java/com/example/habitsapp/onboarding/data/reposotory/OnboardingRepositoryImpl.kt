package com.example.habitsapp.onboarding.data.reposotory

import android.content.SharedPreferences
import com.example.habitsapp.onboarding.domain.repository.OnboardingRepository
import androidx.core.content.edit

class OnboardingRepositoryImpl(
    private val sharedPreferences: SharedPreferences
) : OnboardingRepository {

    override fun hasSeenOnboarding(): Boolean =
        sharedPreferences.getBoolean(HAS_SEEN_ONBOARDING, false)

    override fun completeOnboarding() {
        sharedPreferences.edit { putBoolean(HAS_SEEN_ONBOARDING, true) }
    }

    companion object {
        private const val HAS_SEEN_ONBOARDING = "has_seen_onboarding"
    }
}