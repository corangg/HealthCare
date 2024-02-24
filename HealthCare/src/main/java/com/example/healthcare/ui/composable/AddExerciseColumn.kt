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
   // val exerciseList by viewModel.exerciseList.observeAsState(initial = mutableListOf())
    val exerciseList by viewModel.exerciseList.observeAsState(initial = listOf())

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .background(Color(0xFF121212))
            .width(116.dp)) {
        Text(
            text = day,
            style = TextStyle(color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold),
        )


        exerciseList.forEach { exercise ->
            key(exercise.id) {
                SelectExerciseSpinner(
                    viewModel = viewModel,
                    exercise = exercise.name,
                    onExerciseSelected = { newName ->
                        viewModel.updateExerciseAt(exercise.id, newName)
                    },
                    onDeleteClicked = {
                        viewModel.deleteExercise(exercise.id)
                    }
                )
            }
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