package com.example.healthcare.ui.composable.Main.Record

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
import androidx.compose.material.Shapes
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.healthcare.ChartData
import com.example.healthcare.VIewModel.MainViewModel
import com.example.healthcare.chartDataList
import com.example.healthcare.ui.composable.AddExerciseColumn
import com.example.healthcare.ui.composable.HorizontalScrollView
import com.example.healthcare.ui.composable.SelectExerciseSpinner
/*import com.patrykandpatrick.vico.compose.chart.Chart
import com.patrykandpatrick.vico.compose.chart.column.columnChart
import com.patrykandpatrick.vico.compose.chart.line.lineChart
import com.patrykandpatrick.vico.compose.component.lineComponent
import com.patrykandpatrick.vico.compose.style.ChartStyle
import com.patrykandpatrick.vico.core.chart.values.AxisValuesOverrider
import com.patrykandpatrick.vico.core.component.text.TextComponent
import com.patrykandpatrick.vico.core.entry.ChartEntryModelProducer
import com.patrykandpatrick.vico.core.entry.entryOf*/

@Composable
fun RecordView(){
    val viewModel: MainViewModel = hiltViewModel()
    val scrollState = rememberScrollState()
    val selectExerciseTypeList by viewModel.selectExerciseTypeList.observeAsState(initial = listOf())
    val selectExerciseTypeBoolean by viewModel.selectExerciseTypeBoolean.observeAsState(initial = false)
    val selectExerciseBoolean by viewModel.selectExerciseBoolean.observeAsState(initial = false)

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
                Box(modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp)
                ){
                    /*HorizontalScrollView(
                        modifier = Modifier
                            .padding(20.dp)
                            .widthIn(86.dp)) {
                        exerciseRecordGraph()
                    }*/
                    /*ChartStyle.LineChart(
                        lineChartData = List<ChartData>
                        // 차트 구성에 필요한 추가적인 설정들
                    )*/
                    /*Chart( modifier = Modifier
                        .fillMaxWidth(),
                        chart = columnChart(
                            columns = listOf(
                                lineComponent(
                                    color = Color.White,
                                    thickness = 5.dp
                                )
                            ),
                            dataLabel = TextComponent.Builder().build(),
                            axisValuesOverrider = AxisValuesOverrider.fixed(minY = 0f, maxY = 100f)
                        )
                        , chartModelProducer = ChartEntryModelProducer(
                            listOf(
                                entryOf(x = 0f, y = 60f),
                                entryOf(x = 1f, y = 100f)
                            )
                        ))
*/


                }



                if(selectExerciseBoolean){
                    val selectedOption = remember { mutableStateOf("무게") }
                    val options = listOf("무게", "세트", "횟수")
                    exerciseRadioButtonRow(selectedOption = selectedOption, options = options, onSelect = viewModel::selectExerciseInfoRadio)
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
fun exerciseRadioButtonRow(selectedOption : MutableState<String>, options : List<String>, onSelect: (String) -> Unit){
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