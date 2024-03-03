package com.example.healthcare.VIewModel

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.healthcare.DB.PhsicalInfoDB
import com.example.healthcare.PhsicalInfo
import com.example.healthcare.Repository.PhsicalInfoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val phsicalInfoRepository: PhsicalInfoRepository): ViewModel() {
    var backgroundColor = mutableStateOf(Color(0xFF121212))
        private set

    val profileName : MutableLiveData<String> = MutableLiveData("")
    val profileGender : MutableLiveData<Boolean> = MutableLiveData()
    val profileAge : MutableLiveData<String> = MutableLiveData("")
    val profileHeight : MutableLiveData<String> = MutableLiveData("")
    val profileWeight : MutableLiveData<String> = MutableLiveData("")

    val profileData : MutableLiveData<PhsicalInfo> = MutableLiveData()
    val viewEditCompsable : MutableLiveData<Int> = MutableLiveData(0)


    fun getProfile(){
        viewModelScope.launch{
            profileData.value = phsicalInfoRepository.getAllPhsicalInfos()
            profileName.value = profileData.value?.name
            profileGender.value = profileData.value?.gender
            profileAge.value = profileData.value?.age.toString()
            profileHeight.value = profileData.value?.height.toString()
            profileWeight.value = profileData.value?.weight.toString()
        }
    }

    fun editProfile(item : Int){
        viewEditCompsable.value = item

    }

    fun editCancel(){
        viewEditCompsable.value = 0
    }

    fun editName(){
        viewModelScope.launch {
            phsicalInfoRepository.updateName(profileName.value!!,profileData.value?.name ?:"" )
            getProfile()
        }
    }

    fun editAge(){
        viewModelScope.launch {
            phsicalInfoRepository.updateAge(profileAge.value!!.toInt(),profileData.value?.age ?:-1 )
            getProfile()
        }
    }

    fun editHeight(){
        viewModelScope.launch {
            phsicalInfoRepository.updateHeight(profileHeight.value!!.toFloat(),profileData.value?.height ?:0f )
            getProfile()
        }
    }

    fun editWeight(){
        viewModelScope.launch {
            phsicalInfoRepository.updateWeight(profileWeight.value!!.toFloat(),profileData.value?.weight ?:0f)
            getProfile()
        }
    }


    /*fun editGender(){
        viewModelScope.launch {
            phsicalInfoRepository.updateGender(profileGender.value!!,profileData.value!!.gender )
            getProfile()
        }
    }




    */

}
