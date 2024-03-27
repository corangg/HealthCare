package com.example.healthcare.VIewModel

import android.icu.text.SimpleDateFormat
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.healthcare.ExerciseDataRecord
import com.example.healthcare.ExerciseInfo
import com.example.healthcare.ExerciseItem
import com.example.healthcare.ExerciseRecord
import com.example.healthcare.ExerciseTimeRecord
import com.example.healthcare.ExerciseType
import com.example.healthcare.ExerciseTypeList
import com.example.healthcare.Object
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
    )

    val profileName : MutableLiveData<String> = MutableLiveData("")
    val profileAge : MutableLiveData<String> = MutableLiveData("")
    val profileHeight : MutableLiveData<String> = MutableLiveData("")
    val profileWeight : MutableLiveData<String> = MutableLiveData("")
    val stringDayOfWeek : MutableLiveData<String> = MutableLiveData("")
    val exerciseName : MutableLiveData<String> = MutableLiveData("")
    val lastWeight : MutableLiveData<String> = MutableLiveData()
    val calenderData : MutableLiveData<String> = MutableLiveData("")

    val viewEditCompsable : MutableLiveData<Int> = MutableLiveData(0)
    val selectExerciseRadioInt : MutableLiveData<Int> = MutableLiveData(0)
    val exerciseType : MutableLiveData<Int> = MutableLiveData(-1)

    val profileGender : MutableLiveData<Boolean> = MutableLiveData()
    val editExerciseItem : MutableLiveData<Boolean> = MutableLiveData(false)
    val showAddExerciseView : MutableLiveData<Boolean> = MutableLiveData(false)
    val selectExerciseTypeBoolean : MutableLiveData<Boolean> = MutableLiveData(false)
    val selectExerciseBoolean : MutableLiveData<Boolean> = MutableLiveData(false)

    val todayExerciseList : MutableLiveData<MutableList<MutableList<ExerciseInfo>>> = MutableLiveData(mutableListOf())
    val selectExerciseTypeList : MutableLiveData<MutableList<String>> = MutableLiveData(mutableListOf())
    val selectExerciseDate : MutableLiveData<MutableList<String>> = MutableLiveData(mutableListOf())
    val selectExerciseGraphList : MutableLiveData<MutableList<Float>> = MutableLiveData(mutableListOf())

    var recordExerciseList : MutableList<ExerciseDataRecord> = mutableListOf()
    var list : MutableList<MutableList<ExerciseInfo>> = mutableListOf()
    var todayExerciseRoutine : MutableList<ExerciseItem> = mutableListOf()
    var allWeightDataList : MutableList<WeightData> = mutableListOf()
    var arrayWeightDateList : MutableList<String> = mutableListOf()
    var arrayWeightList : MutableList<Float> = mutableListOf()
    var exerciseTypeList : MutableList<ExerciseTypeList> = mutableListOf()
    var exerciseRecordInfo : MutableList<ExerciseRecord> = mutableListOf()
    var selectExerciseInfo : MutableList<ExerciseTimeRecord> = mutableListOf()

    var previousDate : Int = 0
    var exerciseNumber : Int = -1

    var lastWeightData = WeightData()
    var profileData = PhsicalInfo()


    init {
        initDataSet()
    }

    fun initDataSet(){
        setProfileData()
        viewModelScope.launch{
            recordExerciseList = exerciseRecordRepository.getAllExerciseRecord().toMutableList()
            getAllExerciseRoutine()
            setDayOfWeekData()
            setData()
            exerciseTypeList = exerciseRecordRepository.setExerciseTypeList(recordExerciseList)
            exerciseRecordInfo = exerciseRecordRepository.exerciseRecordOrganize(recordExerciseList)
        }
    }

    fun setProfileData(){
        viewModelScope.launch {
            profileData = phsicalInfoRepository.getAllPhsicalInfos()
            lastWeightData = phsicalInfoRepository.getLastWeightData()
            allWeightDataList = phsicalInfoRepository.getAllWeightData().toMutableList()
            arrayWeightDateList = exerciseRecordRepository.arrayWeightDateList(allWeightDataList)
            arrayWeightList = exerciseRecordRepository.arrayWeightList(allWeightDataList)
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
        calenderData.value = exerciseRecordRepository.getCalendar(previousDate)
        setExerciseList()
    }

    fun setExerciseList() {
        todayExerciseList.value = mutableListOf()
        list = mutableListOf()
        todayExerciseRoutine = exerciseLists[exerciseRecordRepository.getDayOfWeekNum(previousDate)].value!!
        list = exerciseRecordRepository.setListSpace(todayExerciseRoutine)
        todayExerciseList.value = exerciseRecordRepository.bindDateExerciseRecord(calenderData.value ?:"", previousDate, recordExerciseList, todayExerciseRoutine,list)
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

    fun updateExerciseWeight(exerciseNumber: Int, index: Int, newWeight: Float) {
        list = todayExerciseList.value!!
        val exerciseList = list[exerciseNumber]
        val updatedExerciseInfo = exerciseList[index].copy(weight = newWeight)
        exerciseList[index] = updatedExerciseInfo
        list[exerciseNumber] = exerciseList

        todayExerciseList.value = list
    }

    fun updateExerciseSet(exerciseNumber: Int, index: Int, newSet: Float) {
        list = todayExerciseList.value!!
        val exerciseList = list[exerciseNumber]
        val updatedExerciseInfo = exerciseList[index].copy(set = newSet)
        exerciseList[index] = updatedExerciseInfo
        list[exerciseNumber] = exerciseList

        todayExerciseList.value = list
    }

    fun updateExerciseNumber(exerciseNumber: Int, index: Int, newNumber: Float) {
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
        val formatter = SimpleDateFormat("yyyyMMdd")
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
            exerciseRecordRepository.insertExerciseRecord(exerciseRecord())
        }
    }

    fun exerciseRecord():ExerciseDataRecord{
        return ExerciseDataRecord(
            timeStamp = recordDate(calenderData.value!!),
            exerciseType = exerciesType(),
        )
    }

    fun exerciesType(): List<ExerciseType> {
        var list : MutableList<ExerciseType> = mutableListOf()
        for(i in 0 until todayExerciseRoutine.size ){
            val exerciseType = ExerciseType(
                exerciseType = todayExerciseRoutine[i].name,
                exerciseInfo = todayExerciseList.value!![i]
            )
            list.add(exerciseType)
        }
        return list
    }

    fun recordDate(calenderDate : String):Long{
        val regex = "[년월일 ]".toRegex()
        val date = calenderDate.replace(regex,"")
        return date.toLong()
    }

    fun selectExerciseType(type : String){
        val typeList = exerciseTypeList.find { it.exerciseType == type }
        selectExerciseTypeList.value = typeList?.exerciseList ?: mutableListOf()
        if(type == "유산소"){
            exerciseType.value = 1
        }else{
            exerciseType.value = 0
        }
        selectExerciseTypeBoolean.value = true
    }

    fun selectExercise(exercise : String){
        for(i in exerciseRecordInfo){
            if(i.exerciseName == exercise){
                selectExerciseInfo = i.exerciseList.toMutableList()
            }
        }
        selectExerciseDate.value = exerciseRecordRepository.selectExerciseDateSet(selectExerciseInfo)
        when(exerciseType.value){
            0-> selectExerciseInfoRadio(Object.anaerobicExerciseTypeList[0],0)
            1-> selectExerciseInfoRadio(Object.cardioExerciseTypeList[0], 1)
        }
        selectExerciseBoolean.value = true
    }

    fun selectExerciseInfoRadio(info : String, type: Int){
        selectExerciseRadioInt.value = exerciseRecordRepository.selectExerciseInfoRadio(info,type)
        selectExerciseGraphList.value = exerciseRecordRepository.selectExerciseGraphListSet(selectExerciseInfo, selectExerciseRadioInt.value ?:0)
    }
}
