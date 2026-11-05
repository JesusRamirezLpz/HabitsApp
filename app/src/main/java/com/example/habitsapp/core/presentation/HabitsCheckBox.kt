package com.example.habitsapp.core.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun HabitsCheckBox(
    modifier: Modifier = Modifier,
    isChecked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    val backgroundColor =
        if (isChecked) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.background
    Box(
        modifier = modifier
            .size(35.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(color = backgroundColor)
    ) {
        IconButton(onClick = { onCheckedChange(!isChecked) }) {
            if (isChecked)
                Icon(
                    Icons.Default.Check,
                    contentDescription = "Check",
                    tint = MaterialTheme.colorScheme.tertiary
                )
        }
    }
}

@Preview
@Composable
fun HabitsCheckBoxPreview() {
    HabitsCheckBox(isChecked = true, onCheckedChange = {})
}

@Preview
@Composable
fun HabitsCheckBoxPreview2() {
    HabitsCheckBox(isChecked = false, onCheckedChange = {})
}