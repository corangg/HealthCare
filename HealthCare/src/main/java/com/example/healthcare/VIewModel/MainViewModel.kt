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

    var todayExerciseRoutine : MutableList<ExerciseItem> = mutableListOf()


    //var todayExerciseList = mutableStateOf<List<List<ExerciseInfo>>>(listOf())

    var list : MutableList<MutableList<ExerciseInfo>> = mutableListOf()

    fun getCurrentDayOfWeek() {
        val calendar = Calendar.getInstance()
        val dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)
        val arrayDayOfTheWeek = arrayOf("일","월","화","수","목","금","토")
        val previousDate = getDayOfWeek(dayOfWeek)
        list = mutableListOf()
        todayExerciseList.value = list

        getCalendar(calendar)
        stringDayOfWeek.value = arrayDayOfTheWeek[previousDate]

        viewModelScope.launch {
            //exerciseRecordRepository.deleteAllExerciseReord()
            val aa =exerciseRecordRepository.getAllExerciseRecord()
            true

        }//확인 끝나면 제거


        todayExerciseRoutine = exerciseLists[previousDate].value!!
        for (i in 0 until todayExerciseRoutine.size){
            //todayExerciseList.add(mutableListOf())
            list.add(mutableListOf())
        }
        //todayExerciseList.value = list
    }

    fun getDayOfWeek(dayOfWeek: Int) : Int{

        var intDayOfWeek = -1
        if(dayOfWeek - 1 + previousDate % 7 > -1){
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
    val recordExerciseList : MutableLiveData<MutableList<ExerciseRecord>> = MutableLiveData(mutableListOf())


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

            viewModelScope.launch {
                getExerciseRecord()//비동기로 돌아서 그런가?

            }

            getCurrentDayOfWeek()
        }
    }

    suspend fun getExerciseRecord(){
        recordExerciseList.value = exerciseRecordRepository.getAllExerciseRecord().toMutableList()
        bindDateExerciseRecord()
    }

    fun bindDateExerciseRecord(){
        todayExerciseList//리스트
        todayExerciseRoutine//어떤 운동 종류인지
        recordExerciseList.value!![0]
        var dateExerciseRecordList = todayExerciseList.value!!
        recordDate()

        for(i in recordExerciseList.value!!){
            if(i.exerciseType.timeStamp == recordDate()){
                for(j in 0 until todayExerciseRoutine.size){
                    if(todayExerciseRoutine[j].name == i.exerciseType.exerciseType){
                        //dateExerciseRecordList[j].add(i.exerciseInfo)
                        i.exerciseInfo
                        dateExerciseRecordList[j] = i.exerciseInfo.toMutableList()
                        true
                    }
                }
            }
        }
        todayExerciseList.value = mutableListOf()
        todayExerciseList.value = dateExerciseRecordList
        true

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
            //bindExerciseInfo()

            for(i in 0 until todayExerciseRoutine.size) {
                exerciseRecordRepository.insertExerciseRecord(exerciseRecord(i))
                true
            }
            checkSaveToast.value = true
        }
    }

    val checkSaveToast : MutableLiveData<Boolean> = MutableLiveData(false)



    fun exerciseRecord(i : Int):ExerciseRecord{
        return ExerciseRecord(
            exerciesType(i),
           /* recordDate(),
            todayExerciseRoutine[i].name,*/
            todayExerciseList.value!![i]//이건 뭐랄까 그날 운동부위 리스트를 한번에 저장한달까?
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

    fun bindTextFieldWeight(weight : String){
        lastWeightData.value = weight
    }

    fun getCurrentTimeOld(): Long {
        val calendar = Calendar.getInstance()
        val formatter = SimpleDateFormat("yyyyMMddHHmmss")
        return formatter.format(calendar.time).toLong()
    }


    val showAddExerciseView : MutableLiveData<Boolean> = MutableLiveData(false)
    val exerciseName : MutableLiveData<String> = MutableLiveData("")
    var exerciseNumber : Int = -1

    val todayExerciseList : MutableLiveData<MutableList<MutableList<ExerciseInfo>>> = MutableLiveData(mutableListOf())

    fun addArrayExercise(){
        val exerciseInfo = ExerciseInfo(exercise = exerciseName.value!!)
        todayExerciseList.value = mutableListOf()
        list[exerciseNumber].add(exerciseInfo)
        todayExerciseList.value = list
        showAddExerciseView.value = false
    }


    fun showAddExerciseView(number: Int){
        exerciseNumber = number
        exerciseName.value = ""
        showAddExerciseView.value = true
    }

    fun hideAddExerciseView(){
        showAddExerciseView.value = false
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
}
