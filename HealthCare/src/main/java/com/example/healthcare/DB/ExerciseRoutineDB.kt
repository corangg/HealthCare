package com.example.healthcare.DB

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.healthcare.Dao.ExerciseDao
import com.example.healthcare.ExerciseItem

@Database(entities = [ExerciseItem::class], version = 1, exportSchema = false)
abstract class ExerciseRoutineDB : RoomDatabase() {
    abstract fun exerciseDao(): ExerciseDao
}
