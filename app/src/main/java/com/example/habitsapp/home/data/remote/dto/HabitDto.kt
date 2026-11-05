package com.example.habitsapp.home.data.remote.dto

data class HabitDto(
    val name: String,
    val frequency: List<Int>,
    val completedDays: List<Long>?,
    val reminder: Long,
    val startDate: Long
)