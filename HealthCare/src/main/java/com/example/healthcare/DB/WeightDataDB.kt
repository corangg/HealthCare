package com.example.healthcare.DB

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.healthcare.Dao.WeightDataDao
import com.example.healthcare.WeightData


@Database(entities = [WeightData::class], version = 1, exportSchema = false)
abstract class WeightDataDB : RoomDatabase(){
    abstract fun weightDao(): WeightDataDao

    companion object{
        @Volatile
        private var INSTANCE: WeightDataDB? = null

        fun getDatabase(context: Context): WeightDataDB{
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    WeightDataDB::class.java,
                    "weight-database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}