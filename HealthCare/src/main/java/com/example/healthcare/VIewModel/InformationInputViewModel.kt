package com.example.healthcare.VIewModel

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.healthcare.ExerciseItem
import java.util.UUID

class InformationInputViewModel: ViewModel() {
    var backgroundColor = mutableStateOf(Color(0xFF121212))
        private set
    val ageValue : MutableLiveData<Float> = MutableLiveData()
    val heightValue : MutableLiveData<Float> = MutableLiveData()
    val weightValue : MutableLiveData<Float> = MutableLiveData()

    //val exerciseList : MutableLiveData<MutableList<String>> = MutableLiveData(mutableListOf())
   // val exerciseList : MutableLiveData<MutableList<ExerciseItem>> = MutableLiveData(mutableListOf())


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

    //private val _exerciseList = MutableLiveData<List<ExerciseItem>>(listOf())
    //val exerciseList: LiveData<List<ExerciseItem>> = _exerciseList
    val exerciseList : MutableLiveData<MutableList<ExerciseItem>> = MutableLiveData(mutableListOf())

    fun addExercise(name: String){
        val newList = exerciseList.value.orEmpty() + ExerciseItem(name = name)
        exerciseList.value = newList.toMutableList()
    }

    fun deleteExercise(itemId: UUID) {
        val newList = exerciseList.value.orEmpty().filterNot { it.id == itemId }
        exerciseList.value = newList.toMutableList()
    }

    fun updateExerciseAt(itemId: UUID, newName: String) {
        val newList = exerciseList.value.orEmpty().map {
            if (it.id == itemId) it.copy(name = newName) else it
        }
        exerciseList.value = newList.toMutableList()
    }
}