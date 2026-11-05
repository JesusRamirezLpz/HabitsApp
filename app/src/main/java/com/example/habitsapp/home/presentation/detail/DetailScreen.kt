package com.example.habitsapp.home.presentation.detail

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.habitsapp.core.presentation.HabitTextfield
import com.example.habitsapp.home.presentation.detail.components.FrequencyDetails
import com.example.habitsapp.home.presentation.detail.components.ReminderDetails
import com.maxkeppeker.sheets.core.models.base.rememberSheetState
import com.maxkeppeler.sheets.clock.ClockDialog
import com.maxkeppeler.sheets.clock.models.ClockConfig
import com.maxkeppeler.sheets.clock.models.ClockSelection
import java.time.LocalTime

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DetailScreen(
    modifier: Modifier = Modifier,
    id: String? = null,
    viewModel: DetailsViewModel = hiltViewModel(),
    onBackPressed: () -> Unit,
    onSaved: () -> Unit
) {
    val state = viewModel.state

    LaunchedEffect(Unit) {
        if (id != null) {
            viewModel.getHabitById(id)
        }
    }

    LaunchedEffect(state.isSaved) {
        if (state.isSaved) {
            onSaved()
        }
    }

    val clockState = rememberSheetState()
    ClockDialog(
        state = clockState, selection = ClockSelection.HoursMinutes { hours, minutes ->
            viewModel.onEvent(DetailEvent.OnReminderChange(LocalTime.of(hours, minutes)))
        }, config = ClockConfig(
            defaultTime = state.reminder,
            is24HourFormat = true
        )
    )

    Scaffold(
        modifier = modifier,
        topBar = { DetailTopAppBar { onBackPressed() } },
        floatingActionButton = {
            DetailsFloatingActionButton { viewModel.onEvent(DetailEvent.OnSaveHabit) }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(horizontal = 12.dp)
        ) {
            HabitTextfield(
                modifier = Modifier.padding(vertical = 8.dp),
                value = state.habitName,
                onValueChange = {
                    viewModel.onEvent(DetailEvent.OnHabitNameChange(it))
                },
                placeholder = "Enter habit name",
                contentDescription = "Habit name",
                backgroundColor = Color.White
            )
            FrequencyDetails(
                selectedDays = state.frequency,
                onFrequencyChange = {
                    viewModel.onEvent(DetailEvent.OnFrequencyChange(it))
                }
            )
            Spacer(modifier = Modifier.height(8.dp))
            ReminderDetails(
                reminder = state.reminder,
                onTimeClick = {
                    clockState.show()
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailTopAppBar(onBackPressed: () -> Unit) {
    CenterAlignedTopAppBar(
        title = { Text(text = "New Habit") },
        navigationIcon = {
            IconButton(onClick = { onBackPressed() }) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
            }
        }
    )
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DetailsFloatingActionButton(onClick: () -> Unit) {
    FloatingActionButton(
        onClick = { onClick() },
        containerColor = MaterialTheme.colorScheme.primary,
        shape = CircleShape
    ) {
        Icon(
            imageVector = Icons.Default.Check,
            contentDescription = "Create Habit",
            tint = MaterialTheme.colorScheme.tertiary
        )
    }
}