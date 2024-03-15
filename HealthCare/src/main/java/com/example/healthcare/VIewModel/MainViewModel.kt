package com.example.healthcare.VIewModel

import android.icu.text.SimpleDateFormat
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.healthcare.ExerciseInfo
import com.example.healthcare.ExerciseItem
import com.example.healthcare.ExerciseRecord
import com.example.healthcare.ExerciseType
import com.example.healthcare.PhsicalInfo
import com.example.healthcare.Repository.ExerciseRecordRepository
import com.example.healthcare.Repository.ExerciseRoutineRepository
import com.example.healthcare.Repository.PhsicalInfoRepository
import com.example.healthcare.WeightData

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.Calendar
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val phsicalInfoRepository: PhsicalInfoRepository,
    private val exerciseRoutineRepository: ExerciseRoutineRepository,
    private val exerciseRecordRepository: ExerciseRecordRepository): ViewModel() {
    var backgroundColor = mutableStateOf(Color(0xFF121212))
        private set

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
    )//이부분 어캐 줄일수 없나?

    val profileName : MutableLiveData<String> = MutableLiveData("")

    val profileAge : MutableLiveData<String> = MutableLiveData("")
    val profileHeight : MutableLiveData<String> = MutableLiveData("")
    val profileWeight : MutableLiveData<String> = MutableLiveData("")
    val stringDayOfWeek : MutableLiveData<String> = MutableLiveData("")
    val exerciseName : MutableLiveData<String> = MutableLiveData("")
    val lastWeight : MutableLiveData<String> = MutableLiveData()
    val calendarData : MutableLiveData<String> = MutableLiveData("")

    val viewEditCompsable : MutableLiveData<Int> = MutableLiveData(0)

    val profileGender : MutableLiveData<Boolean> = MutableLiveData()
    val editExerciseItem : MutableLiveData<Boolean> = MutableLiveData(false)
    val showAddExerciseView : MutableLiveData<Boolean> = MutableLiveData(false)

    val todayExerciseList : MutableLiveData<MutableList<MutableList<ExerciseInfo>>> = MutableLiveData(mutableListOf())

    var recordExerciseList : MutableList<ExerciseRecord> = mutableListOf()
    var list : MutableList<MutableList<ExerciseInfo>> = mutableListOf()
    var todayExerciseRoutine : MutableList<ExerciseItem> = mutableListOf()

    var previousDate : Int = 0
    var exerciseNumber : Int = -1

    var lastWeightData = WeightData()
    var profileData = PhsicalInfo()


    fun initDataSet(){
        setProfileData()
        viewModelScope.launch{
            recordExerciseList = exerciseRecordRepository.getAllExerciseRecord().toMutableList()
            getAllExerciseRoutine()
            setDayOfWeekData()
            setData()
        }
    }

    fun setProfileData(){
        viewModelScope.launch {
            profileData = phsicalInfoRepository.getAllPhsicalInfos()
            lastWeightData = phsicalInfoRepository.getLastWeightData()
        }
    }

    fun setData(){
        lastWeight.value = lastWeightData.weight.toString()

        profileName.value = profileData.name
        profileGender.value = profileData.gender
        profileAge.value = profileData.age.toString()
        profileHeight.value = profileData.height.toString()
        profileWeight.value = profileData.weight.toString()
    }

    suspend fun getAllExerciseRoutine(){
        for (i in 0 until 7){
            exerciseLists[i].value = exerciseRoutineRepository.getExerciseRoutine(i).toMutableList()
        }
    }

    fun setDayOfWeekData(){
        stringDayOfWeek.value = exerciseRecordRepository.getCurrentDayOfWeek(previousDate)
        calendarData.value = exerciseRecordRepository.getCalendar(previousDate)
        setExerciseList()
    }

    fun setExerciseList() {
        val calendar = Calendar.getInstance()
        val dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)
        val dayOfWeekNum = exerciseRecordRepository.getDayOfWeek(dayOfWeek, previousDate)

        todayExerciseList.value = mutableListOf()
        list = mutableListOf()
        todayExerciseRoutine = exerciseLists[dayOfWeekNum].value!!
        for (i in 0 until todayExerciseRoutine.size){
            list.add(mutableListOf())
        }
        bindDateExerciseRecord(list)
    }

    fun bindDateExerciseRecord(list : MutableList<MutableList<ExerciseInfo>>){
        var dateExerciseRecordList = list
        for(i in recordExerciseList){
            if(i.exerciseType.timeStamp == recordDate()){
                for(j in 0 until todayExerciseRoutine.size){
                    if(todayExerciseRoutine[j].name == i.exerciseType.exerciseType){
                        i.exerciseInfo
                        dateExerciseRecordList[j] = i.exerciseInfo.toMutableList()
                    }
                }
            }
        }
        todayExerciseList.value = dateExerciseRecordList
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
            phsicalInfoRepository.updateName(profileName.value!!,profileData.name)
            setProfileData()
        }
    }

    fun editGender(){
        viewModelScope.launch {
            phsicalInfoRepository.updateGender(profileGender.value!!,profileData.gender)
            setProfileData()
        }
    }

    fun editAge(){
        viewModelScope.launch {
            phsicalInfoRepository.updateAge(profileAge.value!!.toInt(),profileData.age)
            setProfileData()
        }
    }

    fun editHeight(){
        viewModelScope.launch {
            phsicalInfoRepository.updateHeight(profileHeight.value!!.toFloat(),profileData.height)
            setProfileData()
        }
    }

    fun editWeight(){
        viewModelScope.launch {
            phsicalInfoRepository.updateWeight(profileWeight.value!!.toFloat(),profileData.weight)
            setProfileData()
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
            if (it.id == itemId) it.copy(name = newName) else it
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

    fun minusDate(){
        previousDate -= 1
        setDayOfWeekData()
    }

    fun plusDate(){
        if(previousDate < 0){
            previousDate += 1
            setDayOfWeekData()
        }
    }

    fun updateExerciseWeight(exerciseNumber: Int, index: Int, newWeight: Int) {
        list = todayExerciseList.value!!
        val exerciseList = list[exerciseNumber]
        val updatedExerciseInfo = exerciseList[index].copy(weight = newWeight)
        exerciseList[index] = updatedExerciseInfo
        list[exerciseNumber] = exerciseList

        todayExerciseList.value = list
    }

    fun updateExerciseSet(exerciseNumber: Int, index: Int, newSet: Int) {
        list = todayExerciseList.value!!
        val exerciseList = list[exerciseNumber]
        val updatedExerciseInfo = exerciseList[index].copy(set = newSet)
        exerciseList[index] = updatedExerciseInfo
        list[exerciseNumber] = exerciseList

        todayExerciseList.value = list
    }

    fun updateExerciseNumber(exerciseNumber: Int, index: Int, newNumber: Int) {
        list = todayExerciseList.value!!
        val exerciseList = list[exerciseNumber]
        val updatedExerciseInfo = exerciseList[index].copy(number = newNumber)
        exerciseList[index] = updatedExerciseInfo
        list[exerciseNumber] = exerciseList

        todayExerciseList.value = list
    }

    fun showAddExerciseView(number: Int){
        exerciseNumber = number
        exerciseName.value = ""
        showAddExerciseView.value = true
    }

    fun hideAddExerciseView(){
        showAddExerciseView.value = false
    }

    fun bindTextFieldWeight(weight : String){
        lastWeight.value = weight
    }

    fun getCurrentTimeOld(): Long {
        val calendar = Calendar.getInstance()
        val formatter = SimpleDateFormat("yyyyMMddHHmmss")
        return formatter.format(calendar.time).toLong()
    }

    fun addArrayExercise(){
        val exerciseInfo = ExerciseInfo(exercise = exerciseName.value!!)
        todayExerciseList.value = mutableListOf()
        list[exerciseNumber].add(exerciseInfo)
        todayExerciseList.value = list
        showAddExerciseView.value = false
    }

    fun saveExercise(){
        val weightData = WeightData(timeStamp = getCurrentTimeOld(), weight = lastWeight.value!!.toFloat())
        viewModelScope.launch {
            phsicalInfoRepository.insertWeightData(weightData)
            for(i in 0 until todayExerciseRoutine.size) {
                exerciseRecordRepository.insertExerciseRecord(exerciseRecord(i))
            }
        }
    }

    fun exerciseRecord(i : Int):ExerciseRecord{
        return ExerciseRecord(
            exerciesType(i),
            todayExerciseList.value!![i]
        )
    }

    fun exerciesType(i : Int): ExerciseType {
        return ExerciseType(recordDate(),
            todayExerciseRoutine[i].name)
    }

    fun recordDate():Long{
        val regex = "[년월일 ]".toRegex()
        val date = calendarData.value!!.replace(regex,"")
        return date.toLong()
    }












    suspend fun bindExerciseInfo(){
        for(i in 0 until todayExerciseRoutine.size){
            exerciseRecordRepository.insertExerciseRecord(exerciseRecord(i))
            /*when(todayExerciseRoutine[i].name){
                "유산소"->{
                    true
                }
                "등"->{
                    true
                }
                "가슴"->{
                    exerciseRecordRepository.insertExerciseRecord(exerciseRecord(i))
                    true
                }
                "하체"->{
                    true
                }
                "어깨"->{
                    true
                }
                "팔"->{
                    true
                }
                "허리"->{
                    true
                }

            }*/
        }
        true
    }




























}
