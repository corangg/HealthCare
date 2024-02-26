package com.example.healthcare.VIewModel

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import com.example.healthcare.DB.ExerciseRoutineDB
import com.example.healthcare.ExerciseItem
import kotlinx.coroutines.launch

class InformationInputViewModel: ViewModel() {
    var backgroundColor = mutableStateOf(Color(0xFF121212))
        private set
    val ageValue : MutableLiveData<Float> = MutableLiveData()
    val heightValue : MutableLiveData<Float> = MutableLiveData()
    val weightValue : MutableLiveData<Float> = MutableLiveData()
    val genderInfo : MutableLiveData<Boolean> = MutableLiveData()
    val name : MutableLiveData<String> = MutableLiveData("")
    val saveExerciseRoutine : MutableLiveData<Unit> = MutableLiveData()

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
        val newList = exerciseLists[day].value.orEmpty().filterNot { it.id == itemId.toString() }
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

    fun saveData(context : Context){
        val db = Room.databaseBuilder(
            context,
            ExerciseRoutineDB::class.java, "exercise-database"
        ).build()

        viewModelScope.launch {
            for(day in 0 .. 6){
                for(i in exerciseLists[day].value.orEmpty()){
                    db.exerciseDao().insertExerciseItem(i)
                }
            }
        }
    }
    /*fun testRead(context: Context){
        val db = Room.databaseBuilder(
            context,
            ExerciseRoutineDB::class.java, "exercise-database"
        ).build()

        viewModelScope.launch {
            val exercise = db.exerciseDao().getExerciseItemsByDay(2)
            true
        }
    }*/
}