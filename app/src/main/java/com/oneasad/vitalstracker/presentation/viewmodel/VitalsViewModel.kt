package com.oneasad.vitalstracker.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import com.oneasad.vitalstracker.data.local.VitalsDatabase
import com.oneasad.vitalstracker.data.model.Vital
import com.oneasad.vitalstracker.data.repository.VitalsRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class VitalsViewModel(application: Application) : AndroidViewModel(application) {
    private val db = Room.databaseBuilder(
        application,
        VitalsDatabase::class.java, "vitals_db"
    ).build()
    private val repository = VitalsRepository(db.vitalsDao())
    val vitals = repository.vitalsList.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    fun addVital(vital: Vital) {
        viewModelScope.launch {
            repository.insertVital(
                Vital(
                    systolic = vital.systolic,
                    diastolic = vital.diastolic,
                    heartRate = vital.heartRate,
                    weight = vital.weight,
                    babyKicks = vital.babyKicks
                ))
        }
    }

    fun updateVital(vital: Vital) {
        viewModelScope.launch {
            repository.updateVital(vital)
        }
    }

    fun deleteVital(vital: Vital) {
        viewModelScope.launch {
            repository.deleteVital(vital)
        }
    }

    fun deleteAllVitals() {
        viewModelScope.launch {
            repository.deleteAll()
        }
    }
}
