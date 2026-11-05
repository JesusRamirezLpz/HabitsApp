package com.example.habitsapp.home.presentation.homemain

import android.Manifest
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.habitsapp.R
import com.example.habitsapp.home.presentation.homemain.components.HomeAskPermissions
import com.example.habitsapp.home.presentation.homemain.components.HomeDateSelector
import com.example.habitsapp.home.presentation.homemain.components.HomeHabitItem
import com.example.habitsapp.home.presentation.homemain.components.HomeQuote

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel(),
    navigateToDetails: () -> Unit,
    onEditHabit: (String) -> Unit
) {
    val state = viewModel.state
    Scaffold(
        modifier = modifier,
        topBar = { HomeTopAppBar(navigateToDetails) },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navigateToDetails() },
                containerColor = MaterialTheme.colorScheme.primary,
                shape = CircleShape
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Create Habit",
                    tint = MaterialTheme.colorScheme.tertiary
                )
            }
        }) { innerPadding ->
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            HomeAskPermissions(permission = Manifest.permission.POST_NOTIFICATIONS)
        }
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(start = 16.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            HomeQuote(
                quote = "We First make our habits, and then our habits makes us.",
                author = "Anonimous",
                imageId = R.drawable.welcome_img,
                modifier = Modifier.padding(end = 20.dp)
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Habits".uppercase(),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.tertiary
                )
                Spacer(modifier = Modifier.width(32.dp))
                HomeDateSelector(
                    selectedDate = state.selectedDate,
                    mainDate = state.currentDate,
                    onDateSelected = { date ->
                        viewModel.onEvent(HomeEvent.OnChangeDate(date))
                    },
                )
            }
            LazyColumn {
                val sortedHabits = state.habits.sortedBy { it.name }
                items( sortedHabits.size) { position ->
                    HomeHabitItem(
                        habit = sortedHabits[position],
                        selectedDate = state.selectedDate.toLocalDate(),
                        onChecked = {
                            viewModel.onEvent(HomeEvent.OnCompleteHabit(sortedHabits[position]))
                        },
                        onHabitClick = { onEditHabit(sortedHabits[position].id) }
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeTopAppBar(navigateToDetails: () -> Unit) {
    CenterAlignedTopAppBar(
        title = { Text(text = "Home") },
        navigationIcon = {
            IconButton(onClick = { navigateToDetails() }) {
                Icon(Icons.Default.Settings, contentDescription = "Settings")
            }
        }
    )
}