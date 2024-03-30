package com.example.healthcare.ui.composable.Main.Record

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.healthcare.Object
import com.example.healthcare.R
import com.example.healthcare.UnitList
import com.example.healthcare.VIewModel.MainViewModel
import com.example.healthcare.ui.composable.Common.DrawLineGraph
import com.example.healthcare.ui.composable.Common.SelectExerciseSpinner
import com.example.healthcare.ui.composable.Common.exerciseRadioButtonRow

@Composable
fun RecordView(viewModel: MainViewModel, innerPadding: PaddingValues){
    val scrollState = rememberScrollState()
    val selectExerciseTypeList by viewModel.selectExerciseTypeList.observeAsState(initial = listOf())
    val selectExerciseTypeBoolean by viewModel.selectExerciseTypeBoolean.observeAsState(initial = false)
    val selectExerciseBoolean by viewModel.selectExerciseBoolean.observeAsState(initial = false)
    val selectExerciseRadioInt by viewModel.selectExerciseRadioInt.observeAsState(initial = 0)
    val selectExerciseDateList by viewModel.selectExerciseDate.observeAsState(initial = listOf())
    val selectExerciseGraphList by viewModel.selectExerciseGraphList.observeAsState(initial = listOf())
    val selectExerciseType by viewModel.exerciseType.observeAsState(initial = -1)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = viewModel.backgroundColor.value)
            .padding(innerPadding),
    ){
        Column(modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .background(color = viewModel.backgroundColor.value), horizontalAlignment = Alignment.CenterHorizontally)
        {
            Spacer(modifier = Modifier.height(20.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
                    .background(Color(0xFF2D2D2D), RoundedCornerShape(24.dp))
                    .padding(20.dp)
                , horizontalAlignment = Alignment.CenterHorizontally)
            {
                val unitList = UnitList(Object.weightType,Object.weightUnit)

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painter = painterResource(R.drawable.ic_weight),
                        contentDescription = null,
                        modifier = Modifier
                            .size(42.dp)
                            .clip(CircleShape)
                            .border(border = BorderStroke(3.dp, Color.White), shape = CircleShape)
                            .padding(8.dp),
                        colorFilter = ColorFilter.tint(Color.White)
                    )

                    Text(
                        text = "체중",
                        style = TextStyle(color = Color.White, fontSize = 32.sp, fontWeight = FontWeight.Bold),
                        modifier = Modifier
                            .padding(start = 16.dp)
                            .width(64.dp)
                    )
                }
                Spacer(modifier = Modifier.height(10.dp))

                DrawLineGraph(
                    infoList = viewModel.arrayWeightList ,
                    dateList = viewModel.arrayWeightDateList,
                    exerciseInfoNum = 0,
                    unitList = unitList)
            }

            Row {
                SelectExerciseSpinner(
                    exercise = "선택",
                    onExerciseSelected = {viewModel.selectExerciseType(it) },
                    onDeleteClicked = {  },
                    modifier = Modifier
                        .padding(horizontal = 12.dp)
                        .padding(top = 20.dp),
                    textModifier = Modifier
                        .width(96.dp)
                        .padding(vertical = 10.dp)
                        .wrapContentSize(Alignment.Center),
                    dropDownModifier = Modifier
                        .width(96.dp)
                        .heightIn(max = 240.dp)
                        .background(Color(0xFF2D2D2D), RoundedCornerShape(8.dp))
                        .padding(vertical = 1.dp),
                    list = Object.exerciseTypeList,
                    edit = false,
                    select = true
                )

                SelectExerciseSpinner(
                    exercise = "선택",
                    onExerciseSelected = {viewModel.selectExercise(it) },
                    onDeleteClicked = {  },
                    modifier = Modifier
                        .padding(horizontal = 12.dp)
                        .padding(top = 20.dp),
                    textModifier = Modifier
                        .width(96.dp)
                        .padding(vertical = 10.dp)
                        .wrapContentSize(Alignment.Center),
                    dropDownModifier = Modifier
                        .width(96.dp)
                        .heightIn(max = 240.dp)
                        .background(Color(0xFF2D2D2D), RoundedCornerShape(8.dp))
                        .padding(vertical = 1.dp),
                    list = selectExerciseTypeList,
                    edit = false,
                    select = selectExerciseTypeBoolean
                )
            }


            Spacer(modifier = Modifier.height(20.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
                    .background(Color(0xFF2D2D2D), RoundedCornerShape(24.dp))
                , horizontalAlignment = Alignment.CenterHorizontally)
            {

                if(selectExerciseBoolean){
                    var exerciseInfoList = listOf("")
                    var exerciseUnitList = listOf("")
                    when(selectExerciseType){
                        0->{
                            exerciseInfoList = Object.anaerobicExerciseTypeList
                            exerciseUnitList = Object.anaerobicExerciseUnitList
                        }
                        1->{
                            exerciseInfoList = Object.cardioExerciseTypeList
                            exerciseUnitList = Object.cardioExerciseUnitList
                        }
                    }
                    val unitList = UnitList(exerciseInfoList,exerciseUnitList)

                    Box(modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp)

                    ){
                        DrawLineGraph(
                            infoList = selectExerciseGraphList,
                            exerciseInfoNum = selectExerciseRadioInt,
                            dateList = selectExerciseDateList,
                            unitList = unitList)
                    }

                    exerciseRadioButtonRow(onSelect = viewModel::selectExerciseInfoRadio, exerciseType = exerciseInfoList, type = selectExerciseType)
                    Spacer(modifier = Modifier.height(20.dp))
                }
            }
        }
    }
}


