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
import androidx.compose.foundation.layout.widthIn
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
import com.example.healthcare.VIewModel.MainViewModel
import com.example.healthcare.ui.composable.AddExerciseColumn
import com.example.healthcare.ui.composable.HorizontalScrollView
import com.example.healthcare.ui.composable.SelectExerciseSpinner

@Composable
fun RecordView(){
    val viewModel: MainViewModel = hiltViewModel()
    val scrollState = rememberScrollState()
    val selectExerciseTypeList by viewModel.selectExerciseTypeList.observeAsState(initial = listOf())
    val selectExerciseTypeBoolean by viewModel.selectExerciseTypeBoolean.observeAsState(initial = false)

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
                list = listOf("유산소", "등", "가슴", "하체", "어깨", "팔", "허리"),
                edit = false,
                select = true
            )

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
                list = selectExerciseTypeList,
                edit = false,
                select = selectExerciseTypeBoolean
            )

            Spacer(modifier = Modifier.height(20.dp))

            Box(modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp)
            ){
                /*HorizontalScrollView(
                    modifier = Modifier
                        .padding(20.dp)
                        .widthIn(86.dp)) {

                }*/
            }

        }
    }
}