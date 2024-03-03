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

    /*@Query("UPDATE phsical_info SET gender = :new WHERE gender = :old")
    suspend fun updateGender(old: Boolean, new: Boolean)

    @Query("UPDATE phsical_info SET age = :new WHERE age = :old")
    suspend fun updateAge(old: Int, new: Int)

    @Query("UPDATE phsical_info SET height = :new WHERE height = :old")
    suspend fun updateHeight(old: Float, new: Float)

    @Query("UPDATE phsical_info SET weight = :new WHERE weight = :old")
    suspend fun updateWeight(old: Float, new: Float)*/




}