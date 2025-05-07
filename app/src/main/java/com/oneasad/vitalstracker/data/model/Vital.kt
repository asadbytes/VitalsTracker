package com.oneasad.vitalstracker.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "vitals")
data class Vital (
    @PrimaryKey(autoGenerate = true) val vitalRecordId: Int = 0,
    val systolic: Int,
    val diastolic: Int,
    val heartRate: Int,
    val weight: Float,
    val steps: Int,
    val createdAt: Date = Date()
)