package com.example.habitsapp.home.data.alarm

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.example.habitsapp.R
import com.example.habitsapp.home.data.extensions.goAsyncTask
import com.example.habitsapp.home.domain.alarm.AlarmHandler
import com.example.habitsapp.home.domain.models.Habit
import com.example.habitsapp.home.domain.repository.HomeRepository
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDate
import javax.inject.Inject

@AndroidEntryPoint
class AlarmReceiver : BroadcastReceiver() {
    companion object {
        const val HABIT_ID = "HABIT_ID"
        const val CHANNEL_ID = "habit_channel"
        const val CHANNEL_NAME = "Habit Reminder"
    }

    @Inject
    lateinit var repository: HomeRepository
    @Inject
    lateinit var alarmHandler: AlarmHandler

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onReceive(context: Context?, intent: Intent?) = goAsyncTask {
        if (context == null || intent == null) return@goAsyncTask
        val id = intent.getStringExtra(HABIT_ID) ?: return@goAsyncTask
        val habit = repository.getHabitById(id)
        createNotificationChannel(context)
        if(!habit.completedDays.contains(LocalDate.now())){
            showNotification(habit, context)
        }
        alarmHandler.setRecurringAlarm(habit)
    }

    private fun showNotification(habit: Habit, context: Context) {
        val notificationManager = context.getSystemService(NotificationManager::class.java)
        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setContentTitle(habit.name)
            .setSmallIcon(R.drawable.outline_info_24)
            .setAutoCancel(true)
            .build()
        notificationManager.notify(habit.id.hashCode(), notification)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel(context: Context) {
        val channel = NotificationChannel(
            CHANNEL_ID,
            CHANNEL_NAME,
            NotificationManager.IMPORTANCE_HIGH
        )
        channel.description = " Get your habits reminder!"
        val notificationManager = context.getSystemService(NotificationManager::class.java)
        notificationManager.createNotificationChannel(channel)
    }
}