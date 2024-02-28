package com.example.healthcare.VIewModel

import android.app.Application
import android.content.Context
import androidx.compose.material.SnackbarDefaults.backgroundColor
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import com.example.healthcare.DB.PhsicalInfoDB
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(): ViewModel() {
    var backgroundColor = mutableStateOf(Color(0xFF121212))
        private set

    val profileName : MutableLiveData<String> = MutableLiveData("")


    fun getName(context : Context){
        viewModelScope.launch{
            profileName.value = PhsicalInfoDB.getDatabase(context.applicationContext).phsicalDao().getPhsicalInfo()?.name ?:""
        }
    }

}
