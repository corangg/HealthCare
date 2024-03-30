package com.example.healthcare.Repository

import com.example.healthcare.Dao.PhsicalInfoDao
import com.example.healthcare.Dao.WeightDataDao
import com.example.healthcare.PhsicalInfo
import com.example.healthcare.WeightData
import javax.inject.Inject


class PhsicalInfoRepository @Inject constructor(
    private val phsicalInfoDao: PhsicalInfoDao,
    private val weightDataDao: WeightDataDao) {

    suspend fun getAllPhsicalInfos(): PhsicalInfo {
        return phsicalInfoDao.getPhsicalInfo()
    }

    suspend fun deleteAllWeightData(){
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

    /*





    */
}