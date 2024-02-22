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
    var showSpinnerList by remember { mutableStateOf(listOf<UUID>()) }
    var expandedStates by remember { mutableStateOf(mapOf<UUID, Boolean>()) }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .background(Color(0xFF121212))
            .width(116.dp)) {
        Text(
            text = day,
            style = TextStyle(color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold),
        )

        /*showSpinnerList.forEach { (spinnerId, expandedStates) ->
            SelectExerciseSpinner(
                spinnerId = spinnerId,
                expanded = remember { mutableStateOf(isExpanded) },
                onDeleteClicked = { idToRemove ->
                    // spinnerId와 일치하는 ID를 찾아 삭제
                    showSpinnerList = showSpinnerList.toMutableList().also { it.remove(idToRemove) }
                }
            )
        }*/

        expandedStates.keys.forEach { spinnerId ->
            val isExpanded = expandedStates[spinnerId] ?: false
            SelectExerciseSpinner(
                spinnerId = spinnerId,
                expanded = remember { mutableStateOf(isExpanded) }, // expanded 상태를 전달
                // ... 나머지 매개변수 전달 ...
                onDeleteClicked = {
                    // spinnerId와 일치하는 항목을 삭제
                    expandedStates = expandedStates - spinnerId
                }
            )
        }


        Image(
            painter = painterResource(id = R.drawable.ic_add),
            contentDescription = "AddExercise",
            colorFilter = ColorFilter.tint(Color.White),
            modifier = Modifier
                .padding(8.dp)
                .size(30.dp)
                .clickable {
                    //showSpinnerList = showSpinnerList.toMutableList().also { it.add(UUID.randomUUID()) }
                    val newId = UUID.randomUUID()
                    expandedStates = expandedStates + (newId to false)
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