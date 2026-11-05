package com.example.habitsapp.home.presentation.detail

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.habitsapp.home.domain.detail.usecase.GetHabitByIdUseCase
import com.example.habitsapp.home.domain.detail.usecase.InsertHabitUseCase
import com.example.habitsapp.home.domain.models.Habit
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
@RequiresApi(Build.VERSION_CODES.O)
class DetailsViewModel @Inject constructor(
    private val getHabitByIdUseCase: GetHabitByIdUseCase,
    private val insertHabitUseCase: InsertHabitUseCase
) : ViewModel() {

    var state by mutableStateOf(DetailsState())
        private set

    fun onEvent(event: DetailEvent) {
        when (event) {
            is DetailEvent.OnHabitNameChange -> {
                state = state.copy(
                    habitName = event.habitName
                )
            }

            is DetailEvent.OnFrequencyChange -> {
                val frequency = if (state.frequency.contains(event.dayOfWeek)) {
                    state.frequency - event.dayOfWeek
                } else {
                    state.frequency + event.dayOfWeek
                }
                state = state.copy(
                    frequency = frequency
                )
            }

            is DetailEvent.OnReminderChange -> {
                state = state.copy(
                    reminder = event.time
                )
            }

            DetailEvent.OnSaveHabit -> {
                val habit = with(state) {
                   Habit(
                        id = id ?: UUID.randomUUID().toString(),
                        name = habitName,
                        frequency = frequency,
                        reminder = reminder,
                        startDate = startDate
                    )
                }
                viewModelScope.launch {
                    insertHabitUseCase(habit)
                }
                state = state.copy(
                    isSaved = true
                )
            }
        }
    }

    fun getHabitById(id: String?) {
        viewModelScope.launch {
            val habit = getHabitByIdUseCase(id)
            state = state.copy(
                id = habit.id,
                startDate = habit.startDate,
                reminder = habit.reminder,
                habitName = habit.name,
                frequency = habit.frequency,
            )
        }
    }
}