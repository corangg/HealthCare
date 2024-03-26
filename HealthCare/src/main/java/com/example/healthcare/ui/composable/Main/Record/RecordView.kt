package com.example.healthcare.ui.composable.Main.Record

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.healthcare.Object
import com.example.healthcare.UnitList
import com.example.healthcare.VIewModel.MainViewModel
import com.example.healthcare.ui.composable.DrawLineGraph
import com.example.healthcare.ui.composable.SelectExerciseSpinner
import com.example.healthcare.ui.composable.exerciseRadioButtonRow

@Composable
fun RecordView(){
    val viewModel: MainViewModel = hiltViewModel()
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
            .background(color = viewModel.backgroundColor.value),
    ){
        Column(modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .background(color = viewModel.backgroundColor.value), horizontalAlignment = Alignment.CenterHorizontally)
        {
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


