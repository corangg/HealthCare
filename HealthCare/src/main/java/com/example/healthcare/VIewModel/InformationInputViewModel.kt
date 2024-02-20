package com.example.healthcare.VIewModel

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class InformationInputViewModel: ViewModel() {


    var backgroundColor = mutableStateOf(Color(0xFF121212))
        private set
    val heightValue : MutableLiveData<Float> = MutableLiveData()
    fun setHeightValue(value : Float){
        val stringValue = String.format("%.1f",value)
        heightValue.value = stringValue.toFloat()
    }
}