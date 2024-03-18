package com.example.healthcare.Repository

import android.icu.text.SimpleDateFormat
import androidx.lifecycle.MutableLiveData
import com.example.healthcare.Dao.ExerciseDao
import com.example.healthcare.Dao.ExerciseRecordDao
import com.example.healthcare.ExerciseDataRecord
import com.example.healthcare.ExerciseInfo
import java.util.Calendar
import javax.inject.Inject

class ExerciseRecordRepository@Inject constructor(private val exerciseRecordDao: ExerciseRecordDao)  {

    suspend fun getAllExerciseRecord(): List<ExerciseDataRecord>{
        return exerciseRecordDao.getAllExerciseRecords()
    }
    suspend fun getExerciseRecord(): ExerciseDataRecord{
        return exerciseRecordDao.getExerciseRecord()
    }

    suspend fun insertExerciseRecord(exerciseRecord: ExerciseDataRecord){
        exerciseRecordDao.insertExerciseRecord(exerciseRecord)
    }

    suspend fun deleteAllExerciseReord(){
        exerciseRecordDao.deleteAllExerciseReord()
    }

    fun getCurrentDayOfWeek(previousDate: Int): String{
        val calendar = Calendar.getInstance()
        val dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)
        val arrayDayOfTheWeek = arrayOf("일","월","화","수","목","금","토")
        val dayOfWeekNum = getDayOfWeek(dayOfWeek, previousDate)

        return arrayDayOfTheWeek[dayOfWeekNum]
    }

    fun getDayOfWeek(dayOfWeek: Int, previousDate: Int) : Int{
        var intDayOfWeek = -1
        if(dayOfWeek - 1 + previousDate % 7 > -1){
            intDayOfWeek = dayOfWeek - 1 + previousDate%7
        }else{
            intDayOfWeek = dayOfWeek + 6 + previousDate%7
        }
        return intDayOfWeek
    }

    fun getCalendar(previousDate: Int) : String{
        val calendar = Calendar.getInstance()
        val dateFormat = SimpleDateFormat("yyyy년 MM월 dd일")
        calendar.add(Calendar.DAY_OF_YEAR, previousDate)
        return dateFormat.format(calendar.time)
    }
}