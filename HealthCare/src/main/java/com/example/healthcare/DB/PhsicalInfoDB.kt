package com.example.healthcare.DB

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.healthcare.Dao.ExerciseDao
import com.example.healthcare.Dao.PhsicalInfoDao
import com.example.healthcare.PhsicalInfo

@Database(entities = [PhsicalInfo::class], version = 1, exportSchema = false)
abstract class PhsicalInfoDB : RoomDatabase() {
    abstract fun phsicalDao(): PhsicalInfoDao

    companion object {
        @Volatile
        private var INSTANCE: PhsicalInfoDB? = null

        fun getDatabase(context: Context): PhsicalInfoDB {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    PhsicalInfoDB::class.java,
                    "phsical-database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
