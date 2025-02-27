package com.oneasad.vitalstracker.utils

import androidx.room.TypeConverter
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class DateConverter {

    @TypeConverter
    fun fromDate(date: Date?): Long? {
        return date?.time
    }

    @TypeConverter
    fun toDate(timestamp: Long?): Date? {
        return timestamp?.let { Date(it) }
    }
}

fun dateFormatter(date: Date): String {
    val formatter = SimpleDateFormat("EEE, dd MMM yyyy hh:mm a", Locale.getDefault())
    return formatter.format(date)
}