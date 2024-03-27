package com.example.healthcare.ui.composable.Main.Exercise

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.MutableLiveData
import com.example.healthcare.Dao.PhsicalInfoDao
import com.example.healthcare.Dao.WeightDataDao
import com.example.healthcare.ExerciseInfo
import com.example.healthcare.Repository.ExerciseRoutineRepository
import com.example.healthcare.Repository.PhsicalInfoRepository
import com.example.healthcare.VIewModel.MainViewModel

@Composable
fun ExerciseRow(viewModel: MainViewModel, exerciseInfo: ExerciseInfo, exerciseNumber: Int, index:Int){
    var weight by remember {
        mutableStateOf(
            if(viewModel.todayExerciseList.value!![exerciseNumber][index].weight == 0){ "0" }
            else{ viewModel.todayExerciseList.value!![exerciseNumber][index].weight.toString() }) }
    var set by remember {
        mutableStateOf(
            if(viewModel.todayExerciseList.value!![exerciseNumber][index].set == 0){ "0" }
            else{ viewModel.todayExerciseList.value!![exerciseNumber][index].set.toString() }) }
    var number by remember {
        mutableStateOf(
            if(viewModel.todayExerciseList.value!![exerciseNumber][index].number == 0){ "0" }
            else{ viewModel.todayExerciseList.value!![exerciseNumber][index].number.toString() }) }
    val focusRequester = remember { FocusRequester() }
    var isFocused by remember { mutableStateOf(false) }

    if(exerciseInfo.weight != 0){
        weight = exerciseInfo.weight.toString()
    }
    if(exerciseInfo.set != 0){
        set = exerciseInfo.set.toString()
    }
    if(exerciseInfo.number != 0){
        number = exerciseInfo.number.toString()
    }


    Row(
        verticalAlignment = CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .background(Color(0xFF2D2D2D))
            .padding(vertical = 6.dp)) {
        Text(
            text = exerciseInfo.exercise,
            style = TextStyle(color = Color.White, fontSize = 14.sp, textAlign = TextAlign.Center),
            modifier = Modifier.width(60.dp),
            maxLines = 2,
            overflow = TextOverflow.Ellipsis

        )

        Box(
            modifier = Modifier
                .padding(start = 10.dp)
                .height(36.dp)
                .border(2.dp, Color.White, RoundedCornerShape(6.dp))
                .padding(horizontal = 10.dp)
        ) {
            BasicTextField(
                value = weight,
                onValueChange = {
                    weight = it
                    viewModel.updateExerciseWeight(exerciseNumber, index, it.toIntOrNull() ?: exerciseInfo.weight)
                },
                singleLine = true,
                cursorBrush = SolidColor(Color.White),
                modifier = Modifier
                    .width(40.dp)
                    .align(Alignment.CenterStart)
                    .focusRequester(focusRequester)
                    .onFocusChanged {
                        isFocused = it.isFocused
                        if (!it.isFocused && weight.isEmpty()) {
                            weight = "무게"
                        } else if (it.isFocused && weight == "무게") {
                            weight = ""
                        }
                    },
                textStyle = TextStyle(color = Color.White, fontSize = 16.sp, textAlign = TextAlign.Center)
            )
        }
        Spacer(modifier = Modifier.width(16.dp))

        Box(
            modifier = Modifier
                .padding(start = 10.dp)
                .height(36.dp)
                .border(2.dp, Color.White, RoundedCornerShape(6.dp))
                .padding(horizontal = 10.dp)
        ) {
            BasicTextField(
                value = set,
                onValueChange = {
                    set = it
                    viewModel.updateExerciseSet(exerciseNumber, index, it.toIntOrNull() ?: exerciseInfo.set)
                },
                singleLine = true,
                cursorBrush = SolidColor(Color.White),
                modifier = Modifier
                    .width(40.dp)
                    .align(Alignment.CenterStart)
                    .focusRequester(focusRequester)
                    .onFocusChanged {
                        isFocused = it.isFocused
                        if (!it.isFocused && set.isEmpty()) {
                            set = "세트"
                        } else if (it.isFocused && set == "세트") {
                            set = ""
                        }
                    },
                textStyle = TextStyle(color = Color.White, fontSize = 16.sp, textAlign = TextAlign.Center)
            )
        }
        Spacer(modifier = Modifier.width(16.dp))

        Box(
            modifier = Modifier
                .padding(start = 10.dp)
                .height(36.dp)
                .border(2.dp, Color.White, RoundedCornerShape(6.dp))
                .padding(horizontal = 10.dp)
        ) {
            BasicTextField(
                value = number,
                onValueChange = {
                    number = it
                    viewModel.updateExerciseNumber(exerciseNumber, index, it.toIntOrNull() ?: exerciseInfo.number)
                },
                singleLine = true,
                cursorBrush = SolidColor(Color.White),
                modifier = Modifier
                    .width(40.dp)
                    .align(Alignment.CenterStart)
                    .focusRequester(focusRequester)
                    .onFocusChanged {
                        isFocused = it.isFocused
                        if (!it.isFocused && number.isEmpty()) {
                            number = "횟수"
                        } else if (it.isFocused && number == "횟수") {
                            number = ""
                        }
                    },
                textStyle = TextStyle(color = Color.White, fontSize = 16.sp, textAlign = TextAlign.Center)
            )
        }
    }

}
