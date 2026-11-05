package com.example.habitsapp.onboarding.di

import android.content.Context
import android.content.SharedPreferences
import com.example.habitsapp.onboarding.data.reposotory.OnboardingRepositoryImpl
import com.example.habitsapp.onboarding.domain.repository.OnboardingRepository
import com.example.habitsapp.onboarding.domain.usecase.CompleteOnboardingUseCase
import com.example.habitsapp.onboarding.domain.usecase.HasSeenOnboardingUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object OnboardingModule {
    @Provides
    @Singleton
    fun provideOnboardingRepository(sharedPreferences: SharedPreferences): OnboardingRepository =
        OnboardingRepositoryImpl(sharedPreferences)

    @Provides
    @Singleton
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences("onboarding_prefs", Context.MODE_PRIVATE)
    }

    @Provides
    @Singleton
    fun provideHasSeenOnboardingUseCase(onboardingRepository: OnboardingRepository):
            HasSeenOnboardingUseCase = HasSeenOnboardingUseCase(onboardingRepository)

    @Provides
    @Singleton
    fun provideCompleteOnboardingUseCase(onboardingRepository: OnboardingRepository):
            CompleteOnboardingUseCase = CompleteOnboardingUseCase(onboardingRepository)
}