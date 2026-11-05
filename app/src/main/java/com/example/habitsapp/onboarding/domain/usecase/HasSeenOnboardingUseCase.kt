package com.example.habitsapp.onboarding.domain.usecase

import com.example.habitsapp.onboarding.domain.repository.OnboardingRepository

class HasSeenOnboardingUseCase(
    private val onboardingRepository: OnboardingRepository
) {
    operator fun invoke() = onboardingRepository.hasSeenOnboarding()
}