package com.example.healthcare.ui.composable.Main.Exercise

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.healthcare.ExerciseInfo
import com.example.healthcare.VIewModel.MainViewModel

@Composable
fun ExerciseRow(exerciseInfo: ExerciseInfo, exerciseNumber: Int, index:Int){
    var weight by remember { mutableStateOf(exerciseInfo.weight.toString()) }
    val viewModel : MainViewModel = hiltViewModel()

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF2D2D2D))
            .padding(vertical = 10.dp, horizontal = 20.dp)) {
        Text(
            text = exerciseInfo.exercise,
            style = TextStyle(color = Color.White, fontSize = 20.sp),
            modifier = Modifier.width(60.dp)
        )

        Box(
            modifier = Modifier
                .padding(start = 20.dp)
                .height(48.dp)
                .border(2.dp, Color.White, RoundedCornerShape(6.dp))
                .padding(horizontal = 10.dp)
        ) {
            BasicTextField(
                value = weight,
                onValueChange = {
                    weight = it
                    viewModel.updateExerciseWeight(exerciseNumber, index, it.toIntOrNull() ?: exerciseInfo.weight)
                },
                singleLine = true,
                cursorBrush = SolidColor(Color.White),
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterStart),
                textStyle = TextStyle(color = Color.White, fontSize = 16.sp)
            )
        }
    }

}

@Preview(showBackground = true)
@Composable
fun ExerciseRowPreview() {
    ExerciseRow(exerciseInfo = ExerciseInfo("벤치프래스"),1,1)
}