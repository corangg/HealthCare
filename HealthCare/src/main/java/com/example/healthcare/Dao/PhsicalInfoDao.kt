package com.example.healthcare.Dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.healthcare.ExerciseItem

@Dao
interface PhsicalInfoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExerciseItem(item: ExerciseItem)

    @Query("SELECT * FROM exercise_items WHERE dayOfWeek = :dayOfWeek")
    suspend fun getExerciseItemsByDay(dayOfWeek: Int): List<ExerciseItem>

    @Delete
    suspend fun deleteExerciseItem(item: ExerciseItem)
}