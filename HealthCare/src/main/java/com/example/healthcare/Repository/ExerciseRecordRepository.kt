package com.example.healthcare.Repository

import android.icu.text.SimpleDateFormat
import androidx.lifecycle.MutableLiveData
import com.example.healthcare.Dao.ExerciseDao
import com.example.healthcare.Dao.ExerciseRecordDao
import com.example.healthcare.ExerciseDataRecord
import com.example.healthcare.ExerciseInfo
import com.example.healthcare.ExerciseItem
import com.example.healthcare.ExerciseRecord
import com.example.healthcare.ExerciseTimeRecord
import com.example.healthcare.ExerciseTypeList
import com.example.healthcare.Object
import com.example.healthcare.Object.Companion.exerciseTypeList
import com.example.healthcare.WeightData
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

    fun arrayWeightDateList(list : List<WeightData>): MutableList<String>{
        val weightDateList : MutableList<String> = mutableListOf()

        for(i in list){
            val year = i.timeStamp.toString().substring(0,4)
            val month = i.timeStamp.toString().substring(4,6)
            val day = i.timeStamp.toString().substring(6,8)
            val date = year + "년 " + month + "월 " + day + "일"
            weightDateList.add(date)
        }
        return weightDateList
    }

    fun arrayWeightList(list : List<WeightData>): MutableList<Float>{
        val weightList : MutableList<Float> = mutableListOf()

        for(i in list){
            weightList.add(i.weight)
        }
        return weightList
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

    fun getDayOfWeekNum(previousDate: Int) : Int{
        val calendar = Calendar.getInstance()
        val dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)
        val dayOfWeekNum = getDayOfWeek(dayOfWeek, previousDate)
        return dayOfWeekNum
    }

    fun recordDate(calenderDate : String):Long{
        val regex = "[년월일 ]".toRegex()
        val date = calenderDate.replace(regex,"")
        return date.toLong()
    }

    fun bindDateExerciseRecord(calenderDate: String, previousDate: Int, recordExerciseList : MutableList<ExerciseDataRecord>, todayExerciseRoutine : MutableList<ExerciseItem>) : MutableList<MutableList<ExerciseInfo>>{
        val list : MutableList<MutableList<ExerciseInfo>> = mutableListOf()
        for(i in 0 until todayExerciseRoutine.size){
            list.add(mutableListOf())
        }
        var dateExerciseRecordList = list
        for(i in recordExerciseList){
            if(i.timeStamp == recordDate(calenderDate)){
                for(j in 0 until todayExerciseRoutine.size){
                    for(k in i.exerciseType){
                        if(todayExerciseRoutine[j].name == k.exerciseType){
                            dateExerciseRecordList[j] = k.exerciseInfo.toMutableList()//i.exerciseInfo.toMutableList()
                        }
                    }
                }
            }
        }
        for(i in 0 until  dateExerciseRecordList.size){
            if(dateExerciseRecordList[i].size == 0){
                dateExerciseRecordList[i] = getExerciseRoutine(i,previousDate, recordExerciseList, todayExerciseRoutine)
            }
        }
        return dateExerciseRecordList
    }

    fun getExerciseRoutine(routineNumber : Int,previousDate: Int, recordExerciseList : MutableList<ExerciseDataRecord>, todayExerciseRoutine : MutableList<ExerciseItem>) : MutableList<ExerciseInfo>{
        val lastWeekDate = recordDate(getCalendar(-7 + previousDate))
        var exerciseInfoList : MutableList<ExerciseInfo> = mutableListOf()

        for(i in recordExerciseList){
            if(i.timeStamp == lastWeekDate){
                for (j in i.exerciseType){
                    if(todayExerciseRoutine[routineNumber].name == j.exerciseType){
                        for(k in j.exerciseInfo){
                            exerciseInfoList.add(ExerciseInfo(exercise = k.exercise))
                        }
                    }
                }
            }
        }
        return exerciseInfoList
    }

    fun setExerciseTypeList(recordExerciseList : MutableList<ExerciseDataRecord>) : MutableList<ExerciseTypeList>{
        val returnList : MutableList<ExerciseTypeList> = mutableListOf()
        for (i in recordExerciseList) {
            for (j in i.exerciseType) {
                if (!returnList.any { it.exerciseType == j.exerciseType }) {
                    val typeList = ExerciseTypeList(
                        exerciseType = j.exerciseType,
                        exerciseList = j.exerciseInfo.map { it.exercise }.toMutableList()
                    )
                    returnList.add(typeList)
                } else {
                    val targetList = returnList.find { it.exerciseType == j.exerciseType }
                    targetList?.exerciseList?.addAll(j.exerciseInfo.map { it.exercise }.filterNot { targetList.exerciseList.contains(it) })
                }
            }
        }
        return returnList
    }

    fun exerciseRecordOrganize(recordExerciseList : MutableList<ExerciseDataRecord>) : MutableList<ExerciseRecord>{
        val exerciseTimeRecordList : MutableList<ExerciseTimeRecord> = mutableListOf()
        val exerciseRecordInfo : MutableList<ExerciseRecord> = mutableListOf()

        for( i in recordExerciseList){
            for(j in i.exerciseType){
                for(k in j.exerciseInfo){
                    val a = ExerciseTimeRecord(
                        i.timeStamp,
                        k
                    )
                    exerciseTimeRecordList.add(a)
                }
            }
        }

        for(i in exerciseTimeRecordList){
            if(!exerciseRecordInfo.any { it.exerciseName == i.info.exercise }){
                val list : MutableList<ExerciseTimeRecord> = mutableListOf()
                for(j in exerciseTimeRecordList){
                    if(j.info.exercise == i.info.exercise){
                        list.add(j)
                    }
                }
                val exerciseRecord = ExerciseRecord(
                    i.info.exercise,
                    list
                )
                exerciseRecordInfo.add(exerciseRecord)
            }
        }
        return exerciseRecordInfo
    }

    fun selectExerciseDateSet(selectExerciseInfo : MutableList<ExerciseTimeRecord>) : MutableList<String>{
        val list : MutableList<String> = mutableListOf()
        for(i in selectExerciseInfo){
            val year = i.timeStamp.toString().substring(0,4)
            val month = i.timeStamp.toString().substring(4,6)
            val day = i.timeStamp.toString().substring(6,8)
            val date = year + "년 " + month + "월 " + day + "일"
            list.add(date)
        }
        return list
    }

    fun selectExerciseInfoRadio(info : String, type: Int) : Int{
        var index : Int = -1
        when(type){
            0 -> index = Object.anaerobicExerciseTypeList.indexOf(info)
            1 -> index = Object.cardioExerciseTypeList.indexOf(info)
        }
        return index
    }

    fun selectExerciseGraphListSet(selectExerciseInfo : MutableList<ExerciseTimeRecord>, index : Int) : MutableList<Float>{
        val list : MutableList<Float> = mutableListOf()
        for(i in selectExerciseInfo){
            when(index){
                0 -> list.add(i.info.weight.toFloat())
                1 -> list.add(i.info.set.toFloat())
                2 -> list.add(i.info.number.toFloat())
            }
        }
        return list
    }
}