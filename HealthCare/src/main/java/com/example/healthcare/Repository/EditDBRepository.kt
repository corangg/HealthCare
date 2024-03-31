package com.example.healthcare.Repository

import androidx.lifecycle.MutableLiveData
import com.example.healthcare.Dao.ExerciseDao
import com.example.healthcare.Dao.ExerciseRecordDao
import com.example.healthcare.Dao.PhsicalInfoDao
import com.example.healthcare.Dao.WeightDataDao
import com.example.healthcare.ExerciseDataRecord
import com.example.healthcare.ExerciseItem
import com.example.healthcare.PhsicalInfo
import com.example.healthcare.WeightData
import javax.inject.Inject


class EditDBRepository @Inject constructor(
    private val phsicalInfoDao: PhsicalInfoDao,
    private val weightDataDao: WeightDataDao,
    private val exerciseDao: ExerciseDao,
    private val exerciseRecordDao: ExerciseRecordDao) {

    //phisicalInfo
    suspend fun getAllPhsicalInfos(): PhsicalInfo {
        return phsicalInfoDao.getPhsicalInfo()
    }

    suspend fun insertPhsicalInfo(phsicalInfo: PhsicalInfo) {
        phsicalInfoDao.insertPhsicalInfo(phsicalInfo)
    }

    suspend fun deletePhsicalInfo(info: PhsicalInfo) {
        phsicalInfoDao.deletePhsicalInfo(info)
    }

    suspend fun updateName(name : String, oldName: String){
        phsicalInfoDao.updateName(newName = name, oldName = oldName)
    }

    suspend fun updateGender(new: Boolean, old:Boolean){
        phsicalInfoDao.updateGender(newGender = new, oldGender = old)
    }

    suspend fun updateAge(new: Int, old: Int){
        phsicalInfoDao.updateAge(newAge = new, oldAge = old)
    }

    suspend fun updateHeight(new: Float, old:Float){
        phsicalInfoDao.updateHeight(newHeight = new, oldHeight = old)
    }

    suspend fun updateWeight(new: Float, old:Float){
        phsicalInfoDao.updateWeight(newWeight = new, oldWeight = old)
    }

    //weightData

    suspend fun deleteAl1lWeightData(){
        weightDataDao.deleteAllWeightData()
    }

    suspend fun getWeightData(): WeightData{
        return weightDataDao.getWeightData()
    }

    suspend fun getAllWeightData():List<WeightData>{
        return weightDataDao.getAllWeightData()
    }

    suspend fun getLastWeightData():WeightData{
        return weightDataDao.getLastWeightValue()
    }

    suspend fun insertWeightData(weightData: WeightData){
        weightDataDao.insertWeightData(weightData)
    }

    //exercise

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

    //exerciseRecord

    suspend fun getAllExerciseRecord(): List<ExerciseDataRecord>{
        return exerciseRecordDao.getAllExerciseRecords()
    }
    suspend fun getExerciseRecord(): ExerciseDataRecord {
        return exerciseRecordDao.getExerciseRecord()
    }

    suspend fun insertExerciseRecord(exerciseRecord: ExerciseDataRecord){
        exerciseRecordDao.insertExerciseRecord(exerciseRecord)
    }

    suspend fun deleteAllExerciseReord(){
        exerciseRecordDao.deleteAllExerciseReord()
    }
}