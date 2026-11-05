package com.example.habitsapp.home.data.alarm

import android.Manifest
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.annotation.RequiresPermission
import com.example.habitsapp.home.data.alarm.AlarmReceiver.Companion.HABIT_ID
import com.example.habitsapp.home.data.extensions.toTimeStamp
import com.example.habitsapp.home.domain.alarm.AlarmHandler
import com.example.habitsapp.home.domain.models.Habit
import dagger.hilt.android.AndroidEntryPoint
import java.time.DayOfWeek
import java.time.ZonedDateTime

@RequiresApi(Build.VERSION_CODES.O)
class AlarmHandlerImpl(
    private val context: Context
) : AlarmHandler {
    private val alarmManager = context.getSystemService(AlarmManager::class.java)

    @RequiresPermission(Manifest.permission.SCHEDULE_EXACT_ALARM)
    override fun setRecurringAlarm(habit: Habit) {
        val nextAlarm = calculateNextAlarm(habit)
        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            nextAlarm.toTimeStamp(),
            createPendingIntent(habit, nextAlarm.dayOfWeek)
        )
    }

    override fun cancelAlarm(habit: Habit) {
        val nextAlarm = calculateNextAlarm(habit)
        val pending = createPendingIntent(habit, nextAlarm.dayOfWeek)
        alarmManager.cancel(pending)
    }

    private fun calculateNextAlarm(habit: Habit): ZonedDateTime {
        val today = ZonedDateTime.now()
        var nextAlarm = ZonedDateTime.of(today.toLocalDate(), habit.reminder, today.zone)

        if (habit.frequency.contains(today.dayOfWeek) && today.isBefore(nextAlarm)) {
            return nextAlarm
        }

        do {
            nextAlarm = nextAlarm.plusDays(1)
        } while (!habit.frequency.contains(nextAlarm.dayOfWeek))

        return nextAlarm
    }

    private fun createPendingIntent(habit: Habit, dayOfWeek: DayOfWeek): PendingIntent {
        val intent = Intent(context, AlarmReceiver::class.java).apply {
            putExtra(HABIT_ID, habit.id)
        }
        return PendingIntent.getBroadcast(
            context,
            habit.id.hashCode() * 10 * dayOfWeek.value,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
    }
}