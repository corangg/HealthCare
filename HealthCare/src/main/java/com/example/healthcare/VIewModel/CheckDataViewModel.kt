package com.example.healthcare.VIewModel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import com.example.healthcare.DB.PhsicalInfoDB
import com.example.healthcare.Repository.EditDBRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class CheckDataViewModel @Inject constructor(private val editDBRepository: EditDBRepository): ViewModel() {

    val checkProfile : MutableLiveData<Boolean> = MutableLiveData()

    fun checkProfileData(){
        viewModelScope.launch {
            val check = editDBRepository.getAllPhsicalInfos().name != ""
            if(check){
                checkProfile.value = true
            }else{
                checkProfile.value = false
            }
        }
    }
}