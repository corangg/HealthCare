package com.example.healthcare.Dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.healthcare.ExerciseDataRecord
import com.example.healthcare.WeightData

@Dao
interface WeightDataDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWeightData(data: WeightData)

    @Query("SELECT * FROM weight_data")
    suspend fun getWeightData(): WeightData

    @Query("SELECT * FROM weight_data")
    suspend fun getAllWeightData(): List<WeightData>

    @Query("DELETE FROM weight_data")
    suspend fun deleteAllWeightData()

    @Delete
    suspend fun deleteWeightData(data: WeightData)

    @Query("SELECT * FROM weight_data ORDER BY timestamp DESC LIMIT 1")
    suspend fun getLastWeightValue(): WeightData
}