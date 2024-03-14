package com.example.healthcare.Dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.healthcare.ExerciseRecord
import com.example.healthcare.PhsicalInfo

@Dao
interface ExerciseRecordDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExerciseRecord(record: ExerciseRecord)

    @Query("SELECT * FROM exercise_records")
    suspend fun getExerciseRecord(): ExerciseRecord

    @Query("SELECT * FROM exercise_records")
    suspend fun getAllExerciseRecords(): List<ExerciseRecord>

    @Delete
    suspend fun deleteExerciseRecord(record: ExerciseRecord)

    @Query("DELETE FROM exercise_records")
    suspend fun deleteAllExerciseReord()
}