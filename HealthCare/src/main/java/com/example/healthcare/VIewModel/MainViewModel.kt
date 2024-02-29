package com.example.healthcare.VIewModel

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.healthcare.DB.PhsicalInfoDB
import com.example.healthcare.PhsicalInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(): ViewModel() {
    var backgroundColor = mutableStateOf(Color(0xFF121212))
        private set

    /*val profileName : MutableLiveData<String> = MutableLiveData("")
    val profileGender : MutableLiveData<String> = MutableLiveData("")
    val profileAge : MutableLiveData<String> = MutableLiveData("")
    val profileHeight : MutableLiveData<String> = MutableLiveData("")
    val profileWeight : MutableLiveData<String> = MutableLiveData("")*/

    val profileData : MutableLiveData<PhsicalInfo> = MutableLiveData()
    val viewEditCompsable : MutableLiveData<Int> = MutableLiveData(0)


    fun getProfile(context : Context){
        viewModelScope.launch{
            //profileName.value = PhsicalInfoDB.getDatabase(context.applicationContext).phsicalDao().getPhsicalInfo()?.name ?:""
            profileData.value = PhsicalInfoDB.getDatabase(context.applicationContext).phsicalDao().getPhsicalInfo()
        }
    }

    fun editProfile(item : Int){
        viewEditCompsable.value = item

    }

}
