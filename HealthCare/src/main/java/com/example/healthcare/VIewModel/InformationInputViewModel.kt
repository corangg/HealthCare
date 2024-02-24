package com.example.healthcare.VIewModel

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class InformationInputViewModel: ViewModel() {
    var backgroundColor = mutableStateOf(Color(0xFF121212))
        private set
    val ageValue : MutableLiveData<Float> = MutableLiveData()
    val heightValue : MutableLiveData<Float> = MutableLiveData()
    val weightValue : MutableLiveData<Float> = MutableLiveData()

    val exerciseList : MutableLiveData<MutableList<String>> = MutableLiveData(mutableListOf())


    fun setAgeValue(value : Float){
        ageValue.value = value
    }

    fun setHeightValue(value : Float){
        val stringValue = String.format("%.1f",value)
        heightValue.value = stringValue.toFloat()
    }

    fun setWeightValue(value : Float){
        val stringValue = String.format("%.2f",value)
        weightValue.value = stringValue.toFloat()
    }

    fun addExercise(exercise : String){
        val updatedList = exerciseList.value.orEmpty().toMutableList().apply {
            add(exercise)
        }
        // MutableLiveData에 새로운 리스트를 할당
        exerciseList.value = updatedList
    }

    fun deleteExercise(position: Int){
        val updatedList = exerciseList.value.orEmpty().toMutableList().apply {
            if (position >= 0 && position < size) {
                removeAt(position)
            }
        }
        // MutableLiveData에 새로운 리스트를 할당
        exerciseList.value = updatedList
    }

    fun updateExerciseAt(index: Int, selectedExercise: String) {
        val list = exerciseList.value ?: return
        if (index >= 0 && index < list.size) {
            list[index] = selectedExercise
            exerciseList.value = list
        }
    }
}