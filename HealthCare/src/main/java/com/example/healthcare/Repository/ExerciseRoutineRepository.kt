package com.example.healthcare.Repository

import com.example.healthcare.Dao.ExerciseDao
import com.example.healthcare.Dao.PhsicalInfoDao
import com.example.healthcare.ExerciseItem
import com.example.healthcare.PhsicalInfo
import javax.inject.Inject

class ExerciseRoutineRepository@Inject constructor(private val exerciseDao: ExerciseDao) {

    suspend fun getExerciseRoutine(day : Int): List<ExerciseItem> {
        return exerciseDao.getExerciseItemsByDay(day)
    }
}