package com.example.healthcare.ui.composable.Main.Exercise

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.runtime.getValue
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.healthcare.StringList
import com.example.healthcare.R
import com.example.healthcare.VIewModel.MainViewModel
import com.example.healthcare.ui.composable.Common.ShowToastMessage

@Composable
fun exerciseView(viewModel: MainViewModel,innerPadding: PaddingValues){
    val dayOfTheWeek by viewModel.stringDayOfWeek.observeAsState()
    val scrollState = rememberScrollState()
    val getDate by viewModel.calenderData.observeAsState(initial = "")
    val exerciseList by viewModel.todayExerciseList.observeAsState(initial = listOf())
    var unitList : List<String> = mutableListOf()
    val showSaveToastMessage by viewModel.showSaveCheckMessage.observeAsState(initial = false)
    //val weight = viewModel.lastWeight.observeAsState().value ?:""//이거 마지막이 아니라 날짜 맞춰야 할꺼같은데??
    val weight = viewModel.recordWeight.observeAsState().value ?:""//이거 마지막이 아니라 날짜 맞춰야 할꺼같은데??

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = viewModel.backgroundColor.value)
            .padding(innerPadding),
    ){
        Column(modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .background(color = viewModel.backgroundColor.value), horizontalAlignment = Alignment.CenterHorizontally) {
            SelectDayOfTheWeek(data = getDate, clickMinus = { viewModel.minusDate() } , clickPlus = {viewModel.plusDate()})

            Text(
                text = dayOfTheWeek + "요일",
                style = TextStyle(color = Color.White, fontSize = 24.sp, fontWeight = FontWeight.Bold),
                modifier = Modifier
                    .padding(top = 20.dp)
            )

            WeightView(modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp)
                .padding(top = 20.dp)
                .background(Color(0xFF2D2D2D), RoundedCornerShape(24.dp))
                .align(Alignment.CenterHorizontally),
                weight = weight,
                valueChange = viewModel::bindTextFieldWeight)

            for(i in 0 until viewModel.todayExerciseRoutine.size){
                if(viewModel.todayExerciseRoutine[i].name == "유산소"){
                    unitList = StringList.cardioExerciseRowString
                }else{
                    unitList = StringList.anaerobicExerciseRowString
                }
                ExerciseTypeView(
                    viewModel = viewModel,
                    exerciseItem = viewModel.todayExerciseRoutine[i].name,
                    onAddClicked = viewModel::showAddExerciseView,
                    exerciseNumber = i,
                    list = exerciseList,
                    unitList = unitList)
            }

            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .padding(top = 30.dp)
                    .size(120.dp, 40.dp)
                    .clip(CircleShape)
                    .background(Color.Transparent)
                    .border(3.dp, Color.White, CircleShape)
                    .clickable { viewModel.saveExercise() }
            ) {
                Text(
                    text = "저장",
                    color = Color.White,
                    modifier = Modifier
                        .align(Alignment.Center)
                )
            }
            Spacer(modifier = Modifier.height(20.dp))
        }

        Box(modifier = Modifier
            .align(Alignment.BottomCenter)
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
            .border(2.dp, Color.White, RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
            .background(Color(0xFF2D2D2D), RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
        ){
            if(viewModel.showAddExerciseView.observeAsState().value == true){
                AddExerciseView(value = viewModel.exerciseName, editClicked = viewModel::addArrayExercise, viewModel)
            }
        }

        if(showSaveToastMessage){
            ShowToastMessage(
                message = "저장 완료",
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 40.dp)
                    .clip(RoundedCornerShape(24.dp))
                    .background(Color.Black)
                    .border(3.dp, Color.White, RoundedCornerShape(24.dp))
                    .padding(horizontal = 32.dp, vertical = 16.dp))
        }
    }
}

@Composable
fun SelectDayOfTheWeek(data : String, clickMinus : () -> Unit, clickPlus : () -> Unit){
    Row(modifier = Modifier.padding(top = 20.dp), verticalAlignment = Alignment.CenterVertically) {
        Image(
            painter = painterResource(id = R.drawable.ic_left),
            contentDescription = null,
            colorFilter = ColorFilter.tint(Color.White),
            modifier = Modifier
                .size(16.dp)
                .clickable(onClick = { clickMinus() })
        )

        Text(
            text = data,
            style = TextStyle(color = Color.White, fontSize = 16.sp),
            modifier = Modifier
                .padding(horizontal = 10.dp)
        )

        Image(
            painter = painterResource(id = R.drawable.ic_right),
            contentDescription = null,
            colorFilter = ColorFilter.tint(Color.White),
            modifier = Modifier
                .size(16.dp)
                .clickable(onClick = { clickPlus() })
        )
    }
}

@Composable
fun WeightView(modifier: Modifier, weight: String, valueChange: (String)->Unit){
    Box(modifier = modifier){

        Row {
            Text(
                text = "몸무게",
                style = TextStyle(color = Color.White, fontSize = 20.sp),
                modifier = Modifier
                    .padding(20.dp)
            )

            Box(
                modifier = Modifier
                    .padding(top = 20.dp)
                    .height(48.dp)
                    .padding(start = 10.dp)
            ) {
                BasicTextField(
                    value = weight,
                    onValueChange = {
                        valueChange(it) },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    cursorBrush = SolidColor(Color.White),
                    modifier = Modifier
                        .drawBehind {
                            drawLine(
                                color = Color.White,
                                start = Offset(0f, size.height + 8.dp.toPx()),
                                end = Offset(size.width, size.height + 8.dp.toPx()),
                                strokeWidth = 1.dp.toPx()
                            )
                        }
                        .padding(end = 12.dp),
                    textStyle = TextStyle(color = Color.White, fontSize = 20.sp, textAlign = TextAlign.End),
                )
            }

            Text(
                text = "kg",
                style = TextStyle(color = Color.White, fontSize = 20.sp),
                modifier = Modifier
                    .padding(20.dp)
            )
        }
    }
}
