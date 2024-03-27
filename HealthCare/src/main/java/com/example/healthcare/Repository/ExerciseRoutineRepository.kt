package com.example.healthcare.Repository

import androidx.lifecycle.MutableLiveData
import com.example.healthcare.Dao.ExerciseDao
import com.example.healthcare.Dao.PhsicalInfoDao
import com.example.healthcare.ExerciseItem
import com.example.healthcare.PhsicalInfo
import java.util.Calendar
import javax.inject.Inject

class ExerciseRoutineRepository@Inject constructor(private val exerciseDao: ExerciseDao) {

    suspend fun getExerciseRoutine(day : Int): List<ExerciseItem> {
        return exerciseDao.getExerciseItemsByDay(day)
    }

    suspend fun saveExerciseRoutine(list : Array<MutableLiveData<MutableList<ExerciseItem>>>){
        exerciseDao.deleteAllExerciseItems()
        for(day in 0 .. 6){
            for(i in list[day].value.orEmpty()){
                exerciseDao.insertExerciseItem(i)
            }
        }
    }


}