package com.example.habitsapp.home.presentation.homemain.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import java.time.ZonedDateTime

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HomeDateSelector(
    modifier: Modifier = Modifier,
    selectedDate: ZonedDateTime,
    mainDate: ZonedDateTime,
    onDateSelected: (ZonedDateTime) -> Unit,
    datesToShow: Int = 5
) {
    LazyRow(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        val dates = (0..4).map { mainDate.minusDays(it.toLong()) }.reversed()
        items(dates.size) {
            HomeItemDateSelector(
                date = dates[it],
                isSelected = selectedDate.toLocalDate() == dates[it].toLocalDate(),
                onClick = {onDateSelected(dates[it])})
        }
    }
}