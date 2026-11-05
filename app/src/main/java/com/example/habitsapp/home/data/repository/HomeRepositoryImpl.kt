package com.example.habitsapp.home.data.repository

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.work.BackoffPolicy
import androidx.work.Constraints
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.example.habitsapp.home.data.extensions.toStartOfDayTimeStamp
import com.example.habitsapp.home.data.local.HomeDao
import com.example.habitsapp.home.data.local.entity.HabitSyncEntity
import com.example.habitsapp.home.data.mapper.toDomain
import com.example.habitsapp.home.data.mapper.toDto
import com.example.habitsapp.home.data.mapper.toEntity
import com.example.habitsapp.home.data.mapper.toHabitSyncEntity
import com.example.habitsapp.home.data.remote.HomeApi
import com.example.habitsapp.home.data.remote.utils.resultOf
import com.example.habitsapp.home.data.sync.HabitSyncWorker
import com.example.habitsapp.home.domain.alarm.AlarmHandler
import com.example.habitsapp.home.domain.models.Habit
import com.example.habitsapp.home.domain.repository.HomeRepository
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import java.time.DayOfWeek
import java.time.Duration
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZonedDateTime

@RequiresApi(Build.VERSION_CODES.O)
class HomeRepositoryImpl(
    private val homeDao: HomeDao,
    private val api: HomeApi,
    private val alarmHandler: AlarmHandler,
    private val workManager: WorkManager
) : HomeRepository {

    override fun getAllHabitsForSelectedDate(date: ZonedDateTime): Flow<List<Habit>> {
        val localFlow = homeDao.getHabitsForSelectedDate(date.toStartOfDayTimeStamp())
            .map { it.map { it.toDomain() } }

        val apiFlow = getHabitsFromApi()

        return localFlow.combine(apiFlow) { db, _ -> db }
    }

    private fun getHabitsFromApi(): Flow<List<Habit>> = flow<List<Habit>> {
        val habits = api.getAllHabits().toDomain()
        insertHabits(habits)
        emit(emptyList())
    }.onStart {
        emit(emptyList())
    }.catch { e ->
        print(e.message)
    }

    override suspend fun insertHabit(habit: Habit) {
        handleAlarm(habit)
        homeDao.insertHabit(habit.toEntity())
        resultOf { api.insertHabit(habit.toDto()) }
            .onFailure {
                homeDao.insertHabitsSync(habit.toHabitSyncEntity())
                println(it.message)
            }
    }

    override suspend fun getHabitById(id: String?): Habit {
        return homeDao.getHabitById(id.orEmpty()).toDomain()
    }

    override suspend fun syncHabits() {
        val worker = OneTimeWorkRequestBuilder<HabitSyncWorker>().setConstraints(
            Constraints.Builder().setRequiredNetworkType(
                NetworkType.CONNECTED
            ).build()
        ).setBackoffCriteria(BackoffPolicy.EXPONENTIAL, Duration.ofMinutes(5)).build()

        workManager.beginUniqueWork("sync_habit_id", ExistingWorkPolicy.REPLACE, worker).enqueue()
    }

    private suspend fun insertHabits(habits: List<Habit>) {
        habits.forEach { habit ->
            handleAlarm(habit)
            homeDao.insertHabit(habit.toEntity())
        }
    }

    private suspend fun handleAlarm(habit: Habit) {
        try {
            val previous = homeDao.getHabitById(habit.id)
            alarmHandler.cancelAlarm(previous.toDomain())
        } catch (_: Exception) {
            Log.e(this.javaClass.simpleName, "Habit doesn't exist ")
        }
        alarmHandler.setRecurringAlarm(habit)
    }

}