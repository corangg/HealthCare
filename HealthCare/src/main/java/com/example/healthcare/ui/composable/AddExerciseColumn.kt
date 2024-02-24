package com.example.healthcare.ui.composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import com.example.healthcare.R
import com.example.healthcare.SpinnerState
import com.example.healthcare.VIewModel.InformationInputViewModel
import com.example.healthcare.ui.theme.HealthCareTheme
import java.util.UUID

@Composable
fun AddExerciseColumn(day : String, viewModel: InformationInputViewModel) {
    //val exerciseList by viewModel.exerciseList.observeAsState(listOf())
    val exerciseList by viewModel.exerciseList.observeAsState(initial = mutableListOf())


    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .background(Color(0xFF121212))
            .width(116.dp)) {
        Text(
            text = day,
            style = TextStyle(color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold),
        )


        exerciseList.forEachIndexed { index, exercise ->
            key(index){
                SelectExerciseSpinner(
                    viewModel = viewModel,
                    exercise = exercise,
                    onExerciseSelected = { selectedExercise ->
                        // 운동이 선택되면 ViewModel의 목록을 업데이트
                        viewModel.updateExerciseAt(index, selectedExercise)
                    },
                    onDeleteClicked = {
                        // 삭제 버튼 클릭 시 해당 항목을 ViewModel의 목록에서 제거
                        viewModel.deleteExercise(index)
                    }
                )
            }

            Spacer(modifier = Modifier.height(8.dp)) // 스피너 사이의 간격
        }

        Image(
            painter = painterResource(id = R.drawable.ic_add),
            contentDescription = "AddExercise",
            colorFilter = ColorFilter.tint(Color.White),
            modifier = Modifier
                .padding(8.dp)
                .size(30.dp)
                .clickable {
                    viewModel.addExercise("선택")
                }
        )
    }
}



@Preview(showBackground = true)
@Composable
fun  AddExerciseColumnPreview() {
    HealthCareTheme {
        AddExerciseColumn("요일",InformationInputViewModel())
    }
}