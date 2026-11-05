package com.example.habitsapp.home.domain.homemain.usecase

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.habitsapp.home.domain.models.Habit
import com.example.habitsapp.home.domain.repository.HomeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import java.time.ZonedDateTime

@RequiresApi(Build.VERSION_CODES.O)
class GetHabitsForDateUseCase(
    private val repository: HomeRepository
) {
    operator fun invoke(date: ZonedDateTime): Flow<List<Habit>> =
        repository.getAllHabitsForSelectedDate(date).distinctUntilChanged().map {
            it.filter { it.frequency.contains(date.dayOfWeek) }
        }
}