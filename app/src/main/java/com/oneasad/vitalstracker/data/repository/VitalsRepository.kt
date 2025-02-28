package com.oneasad.vitalstracker.data.repository

import com.oneasad.vitalstracker.data.local.VitalsDao
import com.oneasad.vitalstracker.data.model.Vital
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

class VitalsRepository(private val dao: VitalsDao) {
    var vitalsList: Flow<List<Vital>> = dao.getAllVitals()
    suspend fun insertVital(vital: Vital) = dao.insertVital(vital)
    suspend fun updateVital(vital: Vital) = dao.update(vital)
    suspend fun getVitalById(id: Int): Vital? {
        return dao.getVitalById(id)
    }
    suspend fun deleteVital(vital: Vital) {
        if (dao.getAllVitals().first().size == 1)
            deleteAll()
        else
            dao.delete(vital)
    }
    suspend fun deleteAll() = dao.deleteAll()
}
