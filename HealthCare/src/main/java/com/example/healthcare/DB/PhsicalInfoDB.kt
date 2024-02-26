package com.example.healthcare.DB

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.healthcare.Dao.ExerciseDao
import com.example.healthcare.Dao.PhsicalInfoDao
import com.example.healthcare.PhsicalInfo

@Database(entities = [PhsicalInfo::class], version = 1, exportSchema = false)
abstract class PhsicalInfoDB : RoomDatabase() {
    abstract fun phsicalDao(): PhsicalInfoDao
}
