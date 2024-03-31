package com.example.healthcare.VIewModel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import com.example.healthcare.DB.PhsicalInfoDB
import com.example.healthcare.ExerciseItem
import com.example.healthcare.PhsicalInfo
import com.example.healthcare.Repository.ExerciseRecordRepository
import com.example.healthcare.Repository.EditDBRepository
import com.example.healthcare.WeightData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InformationInputViewModel @Inject constructor(
    private val editDBRepository: EditDBRepository,
    private val exerciseRecordRepository: ExerciseRecordRepository
): ViewModel() {
    val ageValue : MutableLiveData<Float> = MutableLiveData(0f)
    val heightValue : MutableLiveData<Float> = MutableLiveData(0f)
    val weightValue : MutableLiveData<Float> = MutableLiveData(0f)
    val genderInfo : MutableLiveData<Boolean> = MutableLiveData()
    val name : MutableLiveData<String> = MutableLiveData("")
    val dataSaveFail : MutableLiveData<Int> = MutableLiveData(-1)
    val dataSaveSuccess : MutableLiveData<Unit> = MutableLiveData()


    val sunExerciseList : MutableLiveData<MutableList<ExerciseItem>> = MutableLiveData(mutableListOf())
    val monExerciseList : MutableLiveData<MutableList<ExerciseItem>> = MutableLiveData(mutableListOf())
    val tuesExerciseList : MutableLiveData<MutableList<ExerciseItem>> = MutableLiveData(mutableListOf())
    val wednesExerciseList : MutableLiveData<MutableList<ExerciseItem>> = MutableLiveData(mutableListOf())
    val thursExerciseList : MutableLiveData<MutableList<ExerciseItem>> = MutableLiveData(mutableListOf())
    val friExerciseList : MutableLiveData<MutableList<ExerciseItem>> = MutableLiveData(mutableListOf())
    val saturExerciseList : MutableLiveData<MutableList<ExerciseItem>> = MutableLiveData(mutableListOf())

    val exerciseLists: Array<MutableLiveData<MutableList<ExerciseItem>>> = arrayOf(
        sunExerciseList, monExerciseList, tuesExerciseList,
        wednesExerciseList, thursExerciseList, friExerciseList, saturExerciseList
    )

    fun setNameValue(value: String){
        name.value = value
    }


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

    fun addExercise(name: String, day: Int){
        val newList = exerciseLists[day].value.orEmpty() + ExerciseItem(name = name, dayOfWeek = day)
        exerciseLists[day].value = newList.toMutableList()
    }

    fun deleteExercise(itemId: String, day: Int) {
        val newList = exerciseLists[day].value.orEmpty().filterNot { it.id == itemId }
        exerciseLists[day].value = newList.toMutableList()

    }

    fun updateExerciseAt(itemId: String, newName: String, day: Int) {
        val newList = exerciseLists[day].value.orEmpty().map {
            if (it.id == itemId.toString()) it.copy(name = newName) else it
        }
        exerciseLists[day].value = newList.toMutableList()
    }

    fun selectGender(gender : Boolean){
        genderInfo.value = gender
    }

    fun checkData(): Int{
        return if(name.value == ""){
            1
        }else if(genderInfo.value == null){
            2
        }else if(ageValue.value == 0f){
            3
        }else if(heightValue.value == 0f){
            4
        }else if(weightValue.value == 0f){
            5
        }else{
            0
        }
    }

    fun saveData(){
        if(checkData() == 0){
            val weightData = WeightData(timeStamp = exerciseRecordRepository.getCurrentTimeOld(), weight = weightValue.value?:0f)
            val phsicalInfo = PhsicalInfo(
                name.value!!,
                genderInfo.value!!,
                ageValue.value!!.toInt(),
                heightValue.value!! ,
                weightValue.value!!)

            viewModelScope.launch {
                editDBRepository.saveExerciseRoutine(exerciseLists)
                editDBRepository.insertPhsicalInfo(phsicalInfo)
                editDBRepository.insertWeightData(weightData)
            }

            dataSaveSuccess.value = Unit
        }else{
            dataSaveFail.value = checkData()
        }
    }
}