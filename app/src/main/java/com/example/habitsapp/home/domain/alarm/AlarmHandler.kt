package com.example.habitsapp.home.domain.alarm

import com.example.habitsapp.home.domain.models.Habit

interface AlarmHandler {
    fun setRecurringAlarm(habit: Habit)
    fun cancelAlarm(habit: Habit)
}