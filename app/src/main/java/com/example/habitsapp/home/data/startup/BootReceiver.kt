package com.example.habitsapp.home.data.startup

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import com.example.habitsapp.home.data.extensions.goAsyncTask
import com.example.habitsapp.home.data.local.HomeDao
import com.example.habitsapp.home.data.mapper.toDomain
import com.example.habitsapp.home.domain.alarm.AlarmHandler
import javax.inject.Inject

class BootReceiver: BroadcastReceiver() {
    @Inject
    lateinit var alarmHandler: AlarmHandler

    @Inject
    lateinit var homeDao: HomeDao

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onReceive(context: Context?, intent: Intent?) = goAsyncTask {
        if (context == null || intent == null) return@goAsyncTask
        if(intent.action != Intent.ACTION_BOOT_COMPLETED) return@goAsyncTask

        val items = homeDao.getAllHabits()
        items.forEach { habitEntity ->
            alarmHandler.setRecurringAlarm(habitEntity.toDomain())
        }
    }
}