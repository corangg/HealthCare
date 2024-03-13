package com.example.healthcare.Repository

import com.example.healthcare.Dao.ExerciseDao
import com.example.healthcare.Dao.ExerciseRecordDao
import com.example.healthcare.ExerciseRecord
import javax.inject.Inject

class ExerciseRecordRepository@Inject constructor(private val exerciseRecordDao: ExerciseRecordDao)  {

    suspend fun getExerciseRecord(): List<ExerciseRecord>{
        return exerciseRecordDao.getAllExerciseRecords()
    }

    suspend fun insertExerciseRecord(exerciseRecord: ExerciseRecord){
        exerciseRecordDao.insertExerciseRecord(exerciseRecord)
    }
}