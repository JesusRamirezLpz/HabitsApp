package com.example.habitsapp.home.presentation.homemain

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.habitsapp.home.domain.homemain.usecase.CompleteHabitUseCase
import com.example.habitsapp.home.domain.homemain.usecase.GetHabitsForDateUseCase
import com.example.habitsapp.home.domain.homemain.usecase.SyncHabitUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val completeHabitUseCase: CompleteHabitUseCase,
    private val getHabitsForDateUseCase: GetHabitsForDateUseCase,
    private val getSyncHabitsUseCase: SyncHabitUseCase
) : ViewModel() {
    var state by mutableStateOf(HomeState())
        private set

    private var currentDayJob: Job? = null

    init {
        getHabits()
        viewModelScope.launch {
            getSyncHabitsUseCase()
        }
    }

    fun onEvent(event: HomeEvent) {
        when (event) {
            is HomeEvent.OnChangeDate -> {
                state = state.copy(
                    selectedDate = event.date
                )
                getHabits()
            }

            is HomeEvent.OnCompleteHabit -> {
                viewModelScope.launch {
                    completeHabitUseCase(event.habit, state.selectedDate)
                }
            }
        }
    }

    private fun getHabits() {
        currentDayJob?.cancel()
        currentDayJob = viewModelScope.launch {
            getHabitsForDateUseCase(state.selectedDate).collectLatest { habits ->
                state = state.copy(
                    habits = habits
                )
            }
        }
    }
}
