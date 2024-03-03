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

    @Query("UPDATE phsical_info SET name = :newName WHERE name = :oldName")
    suspend fun updateName(oldName: String, newName: String)

    @Query("UPDATE phsical_info SET gender = :newGender WHERE gender = :oldGender")
    suspend fun updateGender(oldGender: Boolean, newGender: Boolean)

    @Query("UPDATE phsical_info SET age = :newAge WHERE age = :oldAge")
    suspend fun updateAge(oldAge: Int, newAge: Int)

    @Query("UPDATE phsical_info SET height = :newHeight WHERE height = :oldHeight")
    suspend fun updateHeight(oldHeight: Float, newHeight: Float)

    @Query("UPDATE phsical_info SET weight = :newWeight WHERE weight = :oldWeight")
    suspend fun updateWeight(oldWeight: Float, newWeight: Float)






}