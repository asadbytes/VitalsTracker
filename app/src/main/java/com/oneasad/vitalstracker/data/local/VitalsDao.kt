package com.oneasad.vitalstracker.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.oneasad.vitalstracker.data.model.Vital
import kotlinx.coroutines.flow.Flow

@Dao
interface VitalsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertVital(vital: Vital)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(posts: List<Vital>)

    @Query("SELECT * FROM vitals WHERE vitalRecordId = :id")
    suspend fun getVitalById(id: Int): Vital?

    @Query("SELECT * FROM vitals ORDER BY createdAt DESC")
    fun getAllVitals(): Flow<List<Vital>>

    @Update
    suspend fun update(post: Vital)

    @Delete
    suspend fun delete(post: Vital)

    @Query("DELETE FROM vitals")
    suspend fun deleteAll()
}