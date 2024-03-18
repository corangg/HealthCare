package com.example.healthcare.Dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.healthcare.ExerciseDataRecord
import com.example.healthcare.PhsicalInfo

@Dao
interface ExerciseRecordDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExerciseRecord(record: ExerciseDataRecord)

    @Query("SELECT * FROM exercisedata_records")
    suspend fun getExerciseRecord(): ExerciseDataRecord

    @Query("SELECT * FROM exercisedata_records")
    suspend fun getAllExerciseRecords(): List<ExerciseDataRecord>

    @Delete
    suspend fun deleteExerciseRecord(record: ExerciseDataRecord)

    @Query("DELETE FROM exercisedata_records")
    suspend fun deleteAllExerciseReord()
}