package com.example.healthcare.ui.composable.Main.Record

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Shapes
import androidx.compose.material.Surface
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.healthcare.VIewModel.MainViewModel
import com.example.healthcare.ui.composable.AddExerciseColumn
import com.example.healthcare.ui.composable.DrawLineGraph
import com.example.healthcare.ui.composable.HorizontalScrollView
import com.example.healthcare.ui.composable.SelectExerciseSpinner
import com.github.tehras.charts.line.LineChart
import com.github.tehras.charts.line.LineChartData
import com.github.tehras.charts.line.renderer.line.LineDrawer
import com.github.tehras.charts.line.renderer.line.SolidLineDrawer
import com.github.tehras.charts.line.renderer.point.FilledCircularPointDrawer
import com.github.tehras.charts.piechart.animation.simpleChartAnimation
import kotlin.random.Random



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
                list = listOf("유산소", "등", "가슴", "하체", "어깨", "팔", "허리"),
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


                    Box(modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp)

                    ){
                        DrawLineGraph(
                            infoList = selectExerciseGraphList,
                            exerciseInfoNum = selectExerciseRadioInt,
                            dateList = selectExerciseDateList,
                            type = selectExerciseType)
                    }

                    exerciseRadioButtonRow(onSelect = viewModel::selectExerciseInfoRadio, type = selectExerciseType)
                    Spacer(modifier = Modifier.height(20.dp))
                }
            }
        }
    }
}







@Composable
fun exerciseRecordGraph(){

}

@Composable
fun exerciseRadioButtonRow(onSelect: (String) -> Unit, type : Int = 0){
    var selectedOption = remember { mutableStateOf("") }
    var options = listOf("")
    when(type){
        0->{
            selectedOption = remember { mutableStateOf("무게") }
            options = listOf("무게", "세트", "횟수")
        }
        1->{
            selectedOption = remember { mutableStateOf("인클라인") }
            options = listOf("인클라인", "시간", "거리")
        }
    }
    Row() {
        options.forEach { option ->
            exerciseRadioButton(
                label = option,
                isSelected = option == selectedOption.value,
            ){
                selectedOption.value = option
                onSelect(option)
            }
        }
    }
}

@Composable
fun exerciseRadioButton(label: String, isSelected: Boolean, onSelect: () -> Unit){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .width(100.dp)) {
        RadioButton(
            modifier = Modifier
                .padding(end = 1.dp),
            colors = RadioButtonDefaults.colors(
                selectedColor = Color.White
            ),
            selected = isSelected,
            onClick = onSelect
        )
        Text(text = label)
    }
}

@Preview
@Composable
fun AA(){
    //RecordView()
    val selectedOption = remember { mutableStateOf("무게") }
    val options = listOf("무게", "세트", "횟수")
    //exerciseRadioButtonRow(selectedOption = selectedOption, options = options)
}