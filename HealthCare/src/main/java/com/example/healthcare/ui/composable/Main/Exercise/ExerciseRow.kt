package com.example.healthcare.ui.composable.Main.Exercise

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.healthcare.ExerciseInfo
import com.example.healthcare.VIewModel.MainViewModel

@Composable
fun ExerciseRow(viewModel: MainViewModel, exerciseInfo: ExerciseInfo, exerciseNumber: Int, index:Int){
    val focusRequester = remember { FocusRequester() }

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

        exerciseRowTextField(
            value = viewModel.todayExerciseList.value!![exerciseNumber][index].weight,
            valueChange = viewModel::updateExerciseWeight,
            exerciseNumber = exerciseNumber,
            index = index,
            focusRequester = focusRequester)

        Spacer(modifier = Modifier.width(10.dp))

        exerciseRowTextField(
            value = viewModel.todayExerciseList.value!![exerciseNumber][index].set,
            valueChange = viewModel::updateExerciseSet,
            exerciseNumber = exerciseNumber,
            index = index,
            focusRequester = focusRequester)

        Spacer(modifier = Modifier.width(10.dp))

        exerciseRowTextField(
            value = viewModel.todayExerciseList.value!![exerciseNumber][index].number,
            valueChange = viewModel::updateExerciseNumber,
            exerciseNumber = exerciseNumber,
            index = index,
            focusRequester = focusRequester)
    }
}

@Composable
fun exerciseRowTextField(
    value : Float,
    valueChange : (Int, Int, Float)->Unit,
    exerciseNumber: Int,
    index: Int,
    focusRequester: FocusRequester){
    var isFocused by remember { mutableStateOf(false) }
    var textValue by remember {
        mutableStateOf(
            if(value == 0f){ "0" }
            else{ value.toString() }) }
    Box(
        modifier = Modifier
            .padding(start = 10.dp)
            .height(36.dp)
            .border(2.dp, Color.White, RoundedCornerShape(6.dp))
            .padding(horizontal = 10.dp)
    ) {
        BasicTextField(
            value = textValue,
            onValueChange = {
                textValue = it
                valueChange(exerciseNumber,index,it.toFloatOrNull() ?:value)
            },
            singleLine = true,
            cursorBrush = SolidColor(Color.White),
            modifier = Modifier
                .width(46.dp)
                .align(Alignment.CenterStart)
                .focusRequester(focusRequester)
                .onFocusChanged {
                    isFocused = it.isFocused
                    if (!it.isFocused && textValue.isEmpty()) {
                        textValue = "0"
                    } else if (it.isFocused && textValue == "0") {
                        textValue = ""
                    }
                },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            textStyle = TextStyle(color = Color.White, fontSize = 16.sp, textAlign = TextAlign.Center)
        )
    }
}
