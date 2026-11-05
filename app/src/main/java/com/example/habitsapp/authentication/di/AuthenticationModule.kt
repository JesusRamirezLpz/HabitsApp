package com.example.habitsapp.authentication.di

import com.example.habitsapp.authentication.data.EmailMatcherImpl
import com.example.habitsapp.authentication.data.repository.AuthenticationRepositoryImpl
import com.example.habitsapp.authentication.domain.matcher.EmailMatcher
import com.example.habitsapp.authentication.domain.repository.AuthenticationRepository
import com.example.habitsapp.authentication.domain.usecase.LoginUseCase
import com.example.habitsapp.authentication.domain.usecase.SignupUseCase
import com.example.habitsapp.authentication.domain.usecase.ValidateEmailUseCase
import com.example.habitsapp.authentication.domain.usecase.ValidatePasswordUseCase
import com.example.habitsapp.authentication.domain.usecase.GetUserIdUseCase
import com.example.habitsapp.authentication.domain.usecase.LogoutUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthenticationModule {
    @Provides
    @Singleton
    fun provideAuthenticationRepository(): AuthenticationRepository =
        AuthenticationRepositoryImpl()

    @Provides
    @Singleton
    fun provideLoginUseCase(repository: AuthenticationRepository): LoginUseCase =
        LoginUseCase(repository)

    @Provides
    @Singleton
    fun provideValidateEmailUseCase(emailMatcher: EmailMatcher): ValidateEmailUseCase =
        ValidateEmailUseCase(emailMatcher)

    @Provides
    @Singleton
    fun provideValidatePasswordUseCase(): ValidatePasswordUseCase = ValidatePasswordUseCase()


    @Provides
    @Singleton
    fun provideEmailMatcher(): EmailMatcher = EmailMatcherImpl()

    @Provides
    @Singleton
    fun provideSignupUseCase(repository: AuthenticationRepository): SignupUseCase =
        SignupUseCase(repository)

    @Provides
    @Singleton
    fun provideGetUserIdUseCase(repository: AuthenticationRepository): GetUserIdUseCase =
        GetUserIdUseCase(repository)

    @Provides
    @Singleton
    fun provideLogoutUseCase(repository: AuthenticationRepository): LogoutUseCase =
        LogoutUseCase(repository)
}