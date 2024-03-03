package com.example.healthcare.Repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.healthcare.Dao.PhsicalInfoDao
import com.example.healthcare.PhsicalInfo
import javax.inject.Inject


class PhsicalInfoRepository @Inject constructor(private val phsicalInfoDao: PhsicalInfoDao) {

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

    suspend fun updateAge(new: Int, old: Int){
        phsicalInfoDao.updateAge(newAge = new, oldAge = old)
    }

    suspend fun updateHeight(new: Float, old:Float){
        phsicalInfoDao.updateHeight(newHeight = new, oldHeight = old)
    }

    suspend fun updateWeight(new: Float, old:Float){
        phsicalInfoDao.updateWeight(newWeight = new, oldWeight = old)
    }

    /*suspend fun updateGender(new: Boolean, old:Boolean){
        phsicalInfoDao.updateGender(new = new, old = old)
    }





    */
}