package com.example.habitsapp.home.presentation.homemain.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.habitsapp.core.presentation.HabitsCheckBox
import com.example.habitsapp.home.domain.models.Habit
import java.time.LocalDate

@Composable
fun HomeHabitItem(
    modifier: Modifier = Modifier,
    habit: Habit,
    selectedDate: LocalDate,
    onChecked: (Boolean) -> Unit,
    onHabitClick: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(topStart = 12.dp, bottomStart = 12.dp))
            .background(Color.White)
            .padding(19.dp).clickable { onHabitClick() },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = habit.name, fontWeight = FontWeight.Bold, fontSize = 14.sp)
        HabitsCheckBox(isChecked = habit.completedDays.contains(selectedDate)) {
            onChecked(it)
        }
    }
    Spacer(modifier = Modifier.padding(6.dp))
}