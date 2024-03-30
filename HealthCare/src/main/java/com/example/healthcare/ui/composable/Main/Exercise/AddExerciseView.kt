package com.example.healthcare.ui.composable.Main.Exercise

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.MutableLiveData
import com.example.healthcare.VIewModel.MainViewModel

@Composable
fun AddExerciseView(value : MutableLiveData<*>, editClicked: () -> Unit, viewModel: MainViewModel){
    var itemValue by remember { mutableStateOf(value.value.toString()) }
    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp)
                .height(48.dp)
                .border(2.dp, Color.White, RoundedCornerShape(12.dp))
                .padding(horizontal = 10.dp)
        ) {
            BasicTextField(
                value = itemValue,
                onValueChange = {
                    itemValue = it
                    value.value = it},
                singleLine = true,
                cursorBrush = SolidColor(Color.White),
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterStart),
                textStyle = TextStyle(color = Color.White, fontSize = 16.sp)
            )
        }

        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier
            .fillMaxWidth()
            .padding(top = 20.dp)) {
            ExerciseViewButton(
                modifier = Modifier
                    .weight(1f)
                    .clickable { viewModel.hideAddExerciseView() },
                text = "취소")
            
            Spacer(modifier = Modifier.width(10.dp))

            ExerciseViewButton(
                modifier = Modifier
                    .weight(1f)
                    .clickable { editClicked() },
                text = "저장")
        }
        Spacer(modifier = Modifier.height(20.dp))
    }
}

@Composable
fun ExerciseViewButton(modifier: Modifier, text: String){
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .height(40.dp)
            .background(Color.Transparent)
            .border(3.dp, Color.White, RoundedCornerShape(12.dp))
    ) {
        Text(
            text = text,
            color = Color.White
        )
    }
}