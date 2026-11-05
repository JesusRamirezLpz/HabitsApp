package com.example.habitsapp.home.data.sync

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.habitsapp.home.data.local.HomeDao
import com.example.habitsapp.home.data.local.entity.HabitSyncEntity
import com.example.habitsapp.home.data.mapper.toDomain
import com.example.habitsapp.home.data.mapper.toDto
import com.example.habitsapp.home.data.remote.HomeApi
import com.example.habitsapp.home.data.remote.utils.resultOf
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope

@HiltWorker
@RequiresApi(Build.VERSION_CODES.O)
class HabitSyncWorker @AssistedInject constructor(
    @Assisted val context: Context,
    @Assisted val workerParameters: WorkerParameters,
    private val api: HomeApi,
    private val dao: HomeDao
) : CoroutineWorker(context, workerParameters) {
    override suspend fun doWork(): Result {
        val items = dao.getAllHabitsSync()
        if (runAttemptCount >= 3) {
            return Result.failure()
        }
        return try {
            supervisorScope {
                val jobs = items.map { item ->
                    launch {
                        sync(item)
                    }
                }
                jobs.forEach { it.join() }
            }
            Result.success()
        } catch (_: Exception) {
            Result.retry()
        }
    }

    private suspend fun sync(habitSyncEntity: HabitSyncEntity) {
        val habit = dao.getHabitById(habitSyncEntity.id).toDomain().toDto()
        resultOf {
            api.insertHabit(habit)
        }.onSuccess {
            dao.deleteHabitSync(habitSyncEntity)
        }.onFailure {
            throw it
        }
    }
}