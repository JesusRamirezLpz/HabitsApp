package com.example.habitsapp.home.di

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.Room
import androidx.work.WorkManager
import com.example.habitsapp.home.data.alarm.AlarmHandlerImpl
import com.example.habitsapp.home.data.local.HomeDao
import com.example.habitsapp.home.data.local.HomeDatabase
import com.example.habitsapp.home.data.local.typeconverter.HomeTypeConverter
import com.example.habitsapp.home.data.remote.HomeApi
import com.example.habitsapp.home.data.remote.HomeApi.Companion.BASE_URL
import com.example.habitsapp.home.data.repository.HomeRepositoryImpl
import com.example.habitsapp.home.domain.alarm.AlarmHandler
import com.example.habitsapp.home.domain.detail.usecase.GetHabitByIdUseCase
import com.example.habitsapp.home.domain.detail.usecase.InsertHabitUseCase
import com.example.habitsapp.home.domain.homemain.usecase.CompleteHabitUseCase
import com.example.habitsapp.home.domain.homemain.usecase.GetHabitsForDateUseCase
import com.example.habitsapp.home.domain.homemain.usecase.SyncHabitUseCase
import com.example.habitsapp.home.domain.repository.HomeRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
@RequiresApi(Build.VERSION_CODES.O)
object HomeModule {

    @Provides
    @Singleton
    fun provideAlarmHandler(@ApplicationContext context: Context): AlarmHandler =
        AlarmHandlerImpl(context)

    @Provides
    @Singleton
    fun provideHabitDao(@ApplicationContext context: Context): HomeDao =
        Room.databaseBuilder(
            context = context,
            klass = HomeDatabase::class.java,
            name = "habit_database"
        ).addTypeConverter(HomeTypeConverter()).build().habitDao

    @Provides
    @Singleton
    fun provideHomeRepository(
        habitDao: HomeDao,
        api: HomeApi,
        alarmHandler: AlarmHandler,
        workManager: WorkManager
    ): HomeRepository =
        HomeRepositoryImpl(habitDao, api, alarmHandler, workManager)

    @Provides
    @Singleton
    fun provideWorkManager(@ApplicationContext context: Context) = WorkManager.getInstance(context)

    @Provides
    @Singleton
    fun provideCompleteHabitUseCase(repository: HomeRepository): CompleteHabitUseCase =
        CompleteHabitUseCase(repository)

    @Provides
    @Singleton
    fun provideGetHabitsForDateUseCase(repository: HomeRepository): GetHabitsForDateUseCase =
        GetHabitsForDateUseCase(repository)

    @Provides
    @Singleton
    fun provideGetHabitsByIdUseCase(repository: HomeRepository): GetHabitByIdUseCase =
        GetHabitByIdUseCase(repository)

    @Provides
    @Singleton
    fun provideInsertHabitUseCase(repository: HomeRepository): InsertHabitUseCase =
        InsertHabitUseCase(repository)

    @Provides
    @Singleton
    fun provideSyncHabitUseCase(repository: HomeRepository): SyncHabitUseCase =
        SyncHabitUseCase(repository)

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient =
        OkHttpClient.Builder().addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }).build()

    @Provides
    @Singleton
    fun provideHomeApi(okHttpClient: OkHttpClient): HomeApi =
        Retrofit
            .Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(HomeApi::class.java)
}