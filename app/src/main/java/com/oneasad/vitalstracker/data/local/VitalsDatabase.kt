package com.oneasad.vitalstracker.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.oneasad.vitalstracker.data.model.Vital
import com.oneasad.vitalstracker.utils.DateConverter

@Database(entities = [Vital::class], version = 1)
@TypeConverters(DateConverter::class)
abstract class VitalsDatabase : RoomDatabase() {
    abstract fun vitalsDao(): VitalsDao
}
