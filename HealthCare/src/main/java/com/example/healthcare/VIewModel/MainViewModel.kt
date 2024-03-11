package com.example.healthcare.VIewModel

import android.icu.text.SimpleDateFormat
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.healthcare.ExerciseItem
import com.example.healthcare.PhsicalInfo
import com.example.healthcare.Repository.ExerciseRoutineRepository
import com.example.healthcare.Repository.PhsicalInfoRepository
import com.example.healthcare.WeightData

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.DayOfWeek
import java.util.Calendar
import java.util.Date
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val phsicalInfoRepository: PhsicalInfoRepository,
    private val exerciseRoutineRepository: ExerciseRoutineRepository): ViewModel() {
    var backgroundColor = mutableStateOf(Color(0xFF121212))
        private set

    val profileName : MutableLiveData<String> = MutableLiveData("")
    val profileGender : MutableLiveData<Boolean> = MutableLiveData()
    val profileAge : MutableLiveData<String> = MutableLiveData("")
    val profileHeight : MutableLiveData<String> = MutableLiveData("")
    val profileWeight : MutableLiveData<String> = MutableLiveData("")

    val profileData : MutableLiveData<PhsicalInfo> = MutableLiveData()
    val viewEditCompsable : MutableLiveData<Int> = MutableLiveData(0)

    val editExerciseItem : MutableLiveData<Boolean> = MutableLiveData(false)

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

    val calendarData : MutableLiveData<String> = MutableLiveData("")
    val stringDayOfWeek : MutableLiveData<String> = MutableLiveData("")
    var previousDate : Int = 0

    var toDayExerciseList : MutableList<ExerciseItem> = mutableListOf()


    fun getCurrentDayOfWeek() {
        val calendar = Calendar.getInstance()
        val dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)
        getCalendar(calendar)

        val arrayDayOfTheWeek = arrayOf("일","월","화","수","목","금","토")
        val previousDate = getDayOfWeek(dayOfWeek)
        stringDayOfWeek.value = arrayDayOfTheWeek[previousDate]

        toDayExerciseList = exerciseLists[previousDate].value!!
    }

    fun getDayOfWeek(dayOfWeek: Int) : Int{

        var intDayOfWeek = -1
        if(dayOfWeek - 1 + previousDate%7 > -1){
            intDayOfWeek = dayOfWeek - 1 + previousDate%7
        }else{
            intDayOfWeek = dayOfWeek + 6 + previousDate%7
        }
        return intDayOfWeek
    }

    fun minusDate(){
        previousDate -= 1
        getCurrentDayOfWeek()
    }

    fun plusDate(){
        if(previousDate < 0){
            previousDate += 1
            getCurrentDayOfWeek()
        }
    }

    fun getCalendar(calendar: Calendar){
        val dateFormat = SimpleDateFormat("yyyy년 MM월 dd일")
        calendar.add(Calendar.DAY_OF_YEAR, previousDate)
        calendarData.value = dateFormat.format(calendar.time)
    }

    val lastWeightData : MutableLiveData<String> = MutableLiveData()


    fun getDataBase(){
        viewModelScope.launch{
            profileData.value = phsicalInfoRepository.getAllPhsicalInfos()
            getAllExerciseRoutine()
            lastWeightData.value = phsicalInfoRepository.getLastWeightData().weight.toString()

            //phsicalInfoRepository.getWeightData()
            profileName.value = profileData.value?.name
            profileGender.value = profileData.value?.gender
            profileAge.value = profileData.value?.age.toString()
            profileHeight.value = profileData.value?.height.toString()
            profileWeight.value = profileData.value?.weight.toString()

            getCurrentDayOfWeek()
        }
    }

    suspend fun getAllExerciseRoutine(){
        for (i in 0 until 7){
            exerciseLists[i].value = exerciseRoutineRepository.getExerciseRoutine(i).toMutableList()
        }
    }

    fun editProfile(item : Int){
        viewEditCompsable.value = item

    }

    fun editCancel(){
        viewEditCompsable.value = 0
    }

    fun selectGender(gender : Boolean){
        profileGender.value = gender
    }

    fun editName(){
        viewModelScope.launch {
            phsicalInfoRepository.updateName(profileName.value!!,profileData.value?.name ?:"" )
            getDataBase()
        }
    }

    fun editGender(){
        viewModelScope.launch {
            phsicalInfoRepository.updateGender(profileGender.value!!,profileData.value!!.gender )
            getDataBase()
        }
    }

    fun editAge(){
        viewModelScope.launch {
            phsicalInfoRepository.updateAge(profileAge.value!!.toInt(),profileData.value?.age ?:-1 )
            getDataBase()
        }
    }

    fun editHeight(){
        viewModelScope.launch {
            phsicalInfoRepository.updateHeight(profileHeight.value!!.toFloat(),profileData.value?.height ?:0f )
            getDataBase()
        }
    }

    fun editWeight(){
        viewModelScope.launch {
            phsicalInfoRepository.updateWeight(profileWeight.value!!.toFloat(),profileData.value?.weight ?:0f)
            getDataBase()
        }
    }

    fun addExerciseRoutine(name: String, day: Int){
        val newList = exerciseLists[day].value.orEmpty() + ExerciseItem(name = name, dayOfWeek = day)
        exerciseLists[day].value = newList.toMutableList()
    }

    fun deleteExerciseRoutine(itemId: String, day: Int){
        val newList = exerciseLists[day].value.orEmpty().filterNot { it.id == itemId }
        exerciseLists[day].value = newList.toMutableList()
    }

    fun updateExerciseRoutine(itemId: String, newName: String, day: Int){
        val newList = exerciseLists[day].value.orEmpty().map {
            if (it.id == itemId.toString()) it.copy(name = newName) else it
        }
        exerciseLists[day].value = newList.toMutableList()

    }

    fun editExerciseItem(){
        if(editExerciseItem.value == true){
            viewModelScope.launch {
                exerciseRoutineRepository.saveExerciseRoutine(exerciseLists)
                editExerciseItem.value = !editExerciseItem.value!!
            }
        }else if(editExerciseItem.value == false){

            editExerciseItem.value = !editExerciseItem.value!!
        }
    }

    fun saveExercise(){
        val weightData = WeightData(timeStamp = getCurrentTimeOld(), weight = lastWeightData.value!!.toFloat())
        viewModelScope.launch {
            phsicalInfoRepository.insertWeightData(weightData)
        }

    }

    fun bindTextFieldWeight(weight : String){
        lastWeightData.value = weight
    }

    fun getCurrentTimeOld(): Long {
        val calendar = Calendar.getInstance()
        val formatter = SimpleDateFormat("yyyyMMddHHmmss")
        return formatter.format(calendar.time).toLong()
    }
}
