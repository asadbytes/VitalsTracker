package com.oneasad.vitalstracker.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.oneasad.vitalstracker.data.model.Vital
import com.oneasad.vitalstracker.data.repository.VitalsRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class VitalsViewModel(
    private val repository: VitalsRepository
) : ViewModel() {

    val vitals = repository.vitalsList.stateIn(
        viewModelScope,
        SharingStarted.Lazily,
        emptyList()
    )

    fun addVital(vital: Vital) {
        viewModelScope.launch {
            repository.insertVital(
                Vital(
                    systolic = vital.systolic,
                    diastolic = vital.diastolic,
                    heartRate = vital.heartRate,
                    weight = vital.weight,
                    steps = vital.steps
                ))
        }
    }

    suspend fun getVitalByIdSuspend(id: Int): Vital? {
        return repository.getVitalById(id)
    }

    fun updateVital(vital: Vital) {
        viewModelScope.launch {
            repository.updateVital(vital)
        }
    }

    fun deleteVital(vital: Vital) {
        viewModelScope.launch {
            val currentList = vitals.value
            if (currentList.size == 1) {
                repository.deleteAll()
            } else {
                repository.deleteVital(vital)
            }
        }
    }


    fun deleteAllVitals() {
        viewModelScope.launch {
            repository.deleteAll()
        }
    }
}
