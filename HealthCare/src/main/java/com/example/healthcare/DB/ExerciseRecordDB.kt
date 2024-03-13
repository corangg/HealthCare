package com.example.healthcare.DB

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.healthcare.Dao.ExerciseRecordDao
import com.example.healthcare.Dao.PhsicalInfoDao
import com.example.healthcare.ExerciseRecord
import com.example.healthcare.PhsicalInfo

@Database(entities = [ExerciseRecord::class], version = 1, exportSchema = false)
abstract class ExerciseRecordDB : RoomDatabase() {

    abstract fun exerciseRecordDao(): ExerciseRecordDao

    companion object {
        @Volatile
        private var INSTANCE: ExerciseRecordDB? = null

        fun getDatabase(context: Context): ExerciseRecordDB {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ExerciseRecordDB::class.java,
                    "exercise-record-database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}