package com.example.healthcare.ui.composable.Main.Exercise

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.healthcare.ExerciseInfo
import com.example.healthcare.R
import com.example.healthcare.VIewModel.MainViewModel

@Composable
fun ExerciseTypeView(
    viewModel: MainViewModel,
    unitList: List<String>,
    exerciseItem: String,
    onAddClicked: (Int)-> Unit,
    exerciseNumber: Int,
    list: List<MutableList<ExerciseInfo>>){
    Box(modifier = Modifier
        .padding(horizontal = 10.dp)
        .padding(top = 20.dp)
        .background(Color(0xFF2D2D2D), RoundedCornerShape(24.dp))
    ){
        Column(
            modifier = Modifier
                .padding(20.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = exerciseItem,
                style = TextStyle(color = Color.White, fontSize = 20.sp),
                modifier = Modifier
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(top = 10.dp)
                    .padding(start = 50.dp)
                    .height(20.dp)
                    .fillMaxWidth()
            ) {
                for(i in 0..2){
                    Box(
                        modifier = Modifier
                            .padding(start = 20.dp)
                            .height(36.dp)
                    ){
                        Text(
                            text = unitList[i],
                            style = TextStyle(color = Color.White, fontSize = 12.sp, textAlign = TextAlign.Center),
                            modifier = Modifier
                                .width(66.dp)
                        )
                    }
                }
            }

            list.getOrNull(exerciseNumber)?.forEachIndexed { index, exerciseInfo ->
                ExerciseRow(viewModel = viewModel, exerciseInfo = exerciseInfo, exerciseNumber = exerciseNumber, index = index)
            }

            Image(
                painter = painterResource(id = R.drawable.ic_add),
                contentDescription = "AddExercise",
                colorFilter = ColorFilter.tint(Color.White),
                modifier = Modifier
                    .padding(8.dp)
                    .size(30.dp)
                    .clickable {
                        onAddClicked(exerciseNumber)
                    }
            )
        }
    }
}