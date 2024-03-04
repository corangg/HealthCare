package com.example.healthcare.DB

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.healthcare.Dao.ExerciseDao
import com.example.healthcare.ExerciseItem

@Database(entities = [ExerciseItem::class], version = 1, exportSchema = false)
abstract class ExerciseRoutineDB : RoomDatabase() {
    abstract fun exerciseDao(): ExerciseDao

    companion object{
        @Volatile
        private var INSTANCE : ExerciseRoutineDB? = null

        fun getDatabase(context: Context): ExerciseRoutineDB{
            return  INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ExerciseRoutineDB::class.java,
                    "exercise-database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
