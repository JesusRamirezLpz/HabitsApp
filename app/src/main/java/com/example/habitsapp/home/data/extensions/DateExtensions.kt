package com.example.habitsapp.home.data.extensions

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.Instant
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.temporal.ChronoUnit

@RequiresApi(Build.VERSION_CODES.O)
fun ZonedDateTime.toStartOfDayTimeStamp(): Long =
    truncatedTo(ChronoUnit.DAYS).toEpochSecond() * 1000

@RequiresApi(Build.VERSION_CODES.O)
fun Long.toZoneDateTime(): ZonedDateTime = ZonedDateTime.ofInstant(
    Instant.ofEpochMilli(this), ZoneId.systemDefault()
)

@RequiresApi(Build.VERSION_CODES.O)
fun ZonedDateTime.toTimeStamp(): Long = this.toInstant().toEpochMilli()

@RequiresApi(Build.VERSION_CODES.O)
fun LocalDate.toZonedDateTime(): ZonedDateTime = this.atStartOfDay(ZoneId.systemDefault())

@RequiresApi(Build.VERSION_CODES.O)
fun LocalTime.toZoneDateTime(): ZonedDateTime =
    this.atDate(LocalDate.now()).atZone(ZoneId.systemDefault())