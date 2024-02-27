package com.example.healthcare.VIewModel

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import com.example.healthcare.DB.ExerciseRoutineDB
import com.example.healthcare.DB.PhsicalInfoDB
import com.example.healthcare.ExerciseItem
import com.example.healthcare.PhsicalInfo
import com.example.healthcare.Repository.InformationInputRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InformationInputViewModel @Inject constructor(private val informationInputRepository:InformationInputRepository): ViewModel() {
    var backgroundColor = mutableStateOf(Color(0xFF121212))
        private set
    val ageValue : MutableLiveData<Float> = MutableLiveData(0f)
    val heightValue : MutableLiveData<Float> = MutableLiveData(0f)
    val weightValue : MutableLiveData<Float> = MutableLiveData(0f)
    val genderInfo : MutableLiveData<Boolean> = MutableLiveData()
    val name : MutableLiveData<String> = MutableLiveData("")
    val dataSaveFail : MutableLiveData<Int> = MutableLiveData(-1)
    val dataSaveSuccess : MutableLiveData<Unit> = MutableLiveData()
    val checkProfile : MutableLiveData<Boolean> = MutableLiveData(false)

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


    fun checkProfileData(context: Context){
        val Phsicaldb = Room.databaseBuilder(
            context,
            PhsicalInfoDB::class.java, "phsical-database"
        ).build()
        viewModelScope.launch {
            if(Phsicaldb.phsicalDao().getPhsicalInfo().name != ""){
                checkProfile.value = true
            }
        }
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

    fun saveData(context : Context){
        if(checkData() == 0){
            val Exercisedb = Room.databaseBuilder(
                context,
                ExerciseRoutineDB::class.java, "exercise-database"
            ).build()

            val Phsicaldb = Room.databaseBuilder(
                context,
                PhsicalInfoDB::class.java, "phsical-database"
            ).build()

            val phsicalInfo = PhsicalInfo(
                name.value!!,
                genderInfo.value!!,
                ageValue.value!!.toInt(),
                heightValue.value!! ,
                weightValue.value!!)

            viewModelScope.launch {
                for(day in 0 .. 6){
                    for(i in exerciseLists[day].value.orEmpty()){
                        Exercisedb.exerciseDao().insertExerciseItem(i)
                    }
                }
                Phsicaldb.phsicalDao().insertPhsicalInfo(phsicalInfo)
            }

            dataSaveSuccess.value = Unit
        }else{
            dataSaveFail.value = checkData()
        }

    }
}