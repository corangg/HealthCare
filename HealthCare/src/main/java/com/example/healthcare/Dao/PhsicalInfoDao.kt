package com.example.healthcare.Dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.healthcare.ExerciseItem
import com.example.healthcare.PhsicalInfo

@Dao
interface PhsicalInfoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPhsicalInfo(info: PhsicalInfo)

    @Query("SELECT * FROM phsical_info")
    suspend fun getPhsicalInfo(): PhsicalInfo

    @Delete
    suspend fun deletePhsicalInfo(info: PhsicalInfo)
}