package com.example.healthcare.ui.composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.example.healthcare.VIewModel.InformationInputViewModel
import com.example.healthcare.ui.theme.HealthCareTheme
import java.util.UUID

@Composable
fun SelectExerciseSpinner(
    viewModel: InformationInputViewModel, // ViewModel 인스턴스
    exercise: String, // 현재 선택된 운동 이름
    onExerciseSelected: (String) -> Unit, // 운동 선택 콜백
    onDeleteClicked: () -> Unit){
    var expanded by remember { mutableStateOf(false) }
    //var selectedOption by remember { mutableStateOf("선택") }
    val options = listOf("유산소", "등", "가슴", "하체", "어깨", "팔", "허리")
    var selectedOption by remember { mutableStateOf(exercise) }

    Box(modifier = Modifier
        .width(84.dp)
        .padding(4.dp),
        contentAlignment = Alignment.Center,
    ) {

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .background(Color(0xFF2D2D2D), RoundedCornerShape(8.dp))) {
            Text(
                text = selectedOption,
                style = TextStyle(color = Color.White, fontSize = 16.sp),
                modifier = Modifier
                    .clickable { expanded = true }
                    .padding(vertical = 10.dp)
                    .width(56.dp)
                    .wrapContentSize(Alignment.Center),
            )

            Image(
                painter = painterResource(id = R.drawable.ic_delete),
                contentDescription = "DelExercise",
                colorFilter = ColorFilter.tint(Color.White),
                modifier = Modifier
                    .size(22.dp)
                    .padding(end = 4.dp)
                    .clickable(onClick = onDeleteClicked)
            )
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .width(76.dp)
                .heightIn(max = 240.dp)
                .background(Color(0xFF2D2D2D), RoundedCornerShape(8.dp))
                .padding(vertical = 1.dp)
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = { Text(option) },
                    onClick = {
                        selectedOption = option
                        onExerciseSelected(option) // 선택된 운동을 ViewModel에 업데이트
                        expanded = false
                    }
                )
            }
        }
    }
}

/*
@Preview(showBackground = true)
@Composable
fun  SelectExerciseSpinnerPreview() {
    HealthCareTheme {
        SelectExerciseSpinner(onDeleteClicked: () -> Unit)
    }
}*/
