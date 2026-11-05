package com.example.habitsapp.home.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.habitsapp.home.data.local.entity.HabitEntity
import com.example.habitsapp.home.data.local.entity.HabitSyncEntity
import com.example.habitsapp.home.data.local.typeconverter.HomeTypeConverter

@Database(entities = [HabitEntity::class, HabitSyncEntity::class], version = 1)
@TypeConverters(HomeTypeConverter::class)
abstract class HomeDatabase: RoomDatabase() {
    abstract val habitDao: HomeDao
}