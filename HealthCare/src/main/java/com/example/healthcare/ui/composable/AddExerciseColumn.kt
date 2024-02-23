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
fun AddExerciseColumn(day : String) {
    var showSpinnerList by remember { mutableStateOf(listOf(false)) }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .background(Color(0xFF121212))
            .width(116.dp)) {
        Text(
            text = day,
            style = TextStyle(color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold),
        )


        showSpinnerList.forEachIndexed { index, showSpinner ->
            if (showSpinner) {
                SelectExerciseSpinner {
                    // DelExercise를 클릭했을 때 해당 SelectExerciseSpinner를 삭제합니다.
                    showSpinnerList = showSpinnerList.toMutableList().apply { removeAt(index) }
                }
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
                    showSpinnerList = showSpinnerList.toMutableList().apply { add(true) }
                }
        )
    }
}



@Preview(showBackground = true)
@Composable
fun  AddExerciseColumnPreview() {
    HealthCareTheme {
        AddExerciseColumn("요일")
    }
}