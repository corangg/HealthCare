package com.example.healthcare.VIewModel

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.healthcare.DayExerciseSpinner
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

    val sunExerciseList : MutableLiveData<MutableList<ExerciseItem>> = MutableLiveData(mutableListOf())
    val monExerciseList : MutableLiveData<MutableList<ExerciseItem>> = MutableLiveData(mutableListOf())
    val tuesExerciseList : MutableLiveData<MutableList<ExerciseItem>> = MutableLiveData(mutableListOf())
    val wednesExerciseList : MutableLiveData<MutableList<ExerciseItem>> = MutableLiveData(mutableListOf())
    val thursExerciseList : MutableLiveData<MutableList<ExerciseItem>> = MutableLiveData(mutableListOf())
    val friExerciseList : MutableLiveData<MutableList<ExerciseItem>> = MutableLiveData(mutableListOf())
    val saturExerciseList : MutableLiveData<MutableList<ExerciseItem>> = MutableLiveData(mutableListOf())

    val genderInfo : MutableLiveData<Boolean> = MutableLiveData()

    val dayExerciseList : MutableLiveData<DayExerciseSpinner> = MutableLiveData()

    fun addExercise(name: String, day: Int){
       /* val newList = exerciseList.value.orEmpty() + ExerciseItem(name = name)
        exerciseList.value = newList.toMutableList()*/
        when(day){
            0->{val newList = sunExerciseList.value.orEmpty() + ExerciseItem(name = name)
                sunExerciseList.value = newList.toMutableList()}
            1->{val newList = monExerciseList.value.orEmpty() + ExerciseItem(name = name)
                monExerciseList.value = newList.toMutableList()}
            2->{val newList = tuesExerciseList.value.orEmpty() + ExerciseItem(name = name)
                tuesExerciseList.value = newList.toMutableList()}
            3->{val newList = wednesExerciseList.value.orEmpty() + ExerciseItem(name = name)
                wednesExerciseList.value = newList.toMutableList()}
            4->{val newList = thursExerciseList.value.orEmpty() + ExerciseItem(name = name)
                thursExerciseList.value = newList.toMutableList()}
            5->{val newList = friExerciseList.value.orEmpty() + ExerciseItem(name = name)
                friExerciseList.value = newList.toMutableList()}
            6->{val newList = saturExerciseList.value.orEmpty() + ExerciseItem(name = name)
                saturExerciseList.value = newList.toMutableList()}
        }
    }

    fun deleteExercise(itemId: UUID, day: Int) {
        /*val newList = exerciseList.value.orEmpty().filterNot { it.id == itemId }
        exerciseList.value = newList.toMutableList()*/
        when(day){
            0->{val newList = sunExerciseList.value.orEmpty().filterNot { it.id == itemId }
                sunExerciseList.value = newList.toMutableList()}
            1->{val newList = monExerciseList.value.orEmpty().filterNot { it.id == itemId }
                 monExerciseList.value= newList.toMutableList()}
            2->{val newList = tuesExerciseList.value.orEmpty().filterNot { it.id == itemId }
                 tuesExerciseList.value= newList.toMutableList()}
            3->{val newList = wednesExerciseList.value.orEmpty().filterNot { it.id == itemId }
                 wednesExerciseList.value= newList.toMutableList()}
            4->{val newList = thursExerciseList.value.orEmpty().filterNot { it.id == itemId }
                 thursExerciseList.value= newList.toMutableList()}
            5->{val newList = friExerciseList.value.orEmpty().filterNot { it.id == itemId }
                 friExerciseList.value= newList.toMutableList()}
            6->{val newList = saturExerciseList.value.orEmpty().filterNot { it.id == itemId }
                 saturExerciseList.value= newList.toMutableList()}
        }

    }

    fun updateExerciseAt(itemId: UUID, newName: String, day: Int) {
        /*val newList = exerciseList.value.orEmpty().map {
            if (it.id == itemId) it.copy(name = newName) else it
        }
        exerciseList.value = newList.toMutableList()*/
        when(day){
            0->{val newList = sunExerciseList.value.orEmpty().map {
                if (it.id == itemId) it.copy(name = newName) else it
            }
                sunExerciseList.value = newList.toMutableList()}
            1->{val newList = monExerciseList.value.orEmpty().map {
                if (it.id == itemId) it.copy(name = newName) else it
            }
                monExerciseList.value = newList.toMutableList()}
            2->{val newList = tuesExerciseList.value.orEmpty().map {
                if (it.id == itemId) it.copy(name = newName) else it
            }
                tuesExerciseList.value = newList.toMutableList()}
            3->{val newList = wednesExerciseList.value.orEmpty().map {
                if (it.id == itemId) it.copy(name = newName) else it
            }
                wednesExerciseList.value = newList.toMutableList()}
            4->{val newList = thursExerciseList.value.orEmpty().map {
                if (it.id == itemId) it.copy(name = newName) else it
            }
                thursExerciseList.value = newList.toMutableList()}

            5->{val newList = friExerciseList.value.orEmpty().map {
                if (it.id == itemId) it.copy(name = newName) else it
            }
                friExerciseList.value = newList.toMutableList()}
            6->{val newList = saturExerciseList.value.orEmpty().map {
                if (it.id == itemId) it.copy(name = newName) else it
            }
                saturExerciseList.value = newList.toMutableList()}
        }
    }

    fun selectGender(gender : Boolean){
        genderInfo.value = gender
    }
}