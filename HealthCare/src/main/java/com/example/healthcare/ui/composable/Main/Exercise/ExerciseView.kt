package com.example.healthcare.ui.composable.Main.Exercise

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.healthcare.R
import com.example.healthcare.VIewModel.MainViewModel
import com.example.healthcare.ui.composable.Main.Profile.ProfileView

@Composable
fun exerciseView(){

    val viewModel: MainViewModel = hiltViewModel()
    LaunchedEffect(true) {
        viewModel.getDataBase()
        //viewModel.getCurrentDayOfWeek()
    }

    val dayOfTheWeek by viewModel.stringDayOfWeek.observeAsState()
    val scrollState = rememberScrollState()
    val getDate by viewModel.calendarData.observeAsState()

    Column(modifier = Modifier
        .fillMaxSize()
        .verticalScroll(scrollState)
        .background(color = viewModel.backgroundColor.value), horizontalAlignment = Alignment.CenterHorizontally)
    {

        Row(modifier = Modifier.padding(top = 20.dp), verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(id = R.drawable.ic_left),
                contentDescription = "",
                colorFilter = ColorFilter.tint(Color.White),
                modifier = Modifier
                    .size(16.dp)
                    .clickable { viewModel.minusDate() }
            )

            Text(
                text = getDate!!,
                style = TextStyle(color = Color.White, fontSize = 16.sp),
                modifier = Modifier
                    .padding(horizontal = 10.dp)
            )

            Image(
                painter = painterResource(id = R.drawable.ic_right),

                contentDescription = "",
                colorFilter = ColorFilter.tint(Color.White),
                modifier = Modifier
                    .size(16.dp)
                    .clickable { viewModel.plusDate() }
            )
        }

        Text(
            text = dayOfTheWeek + "요일",
            style = TextStyle(color = Color.White, fontSize = 24.sp, fontWeight = FontWeight.Bold),
            modifier = Modifier
                .padding(top = 20.dp)

        )

        Box(modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
            .padding(top = 20.dp)
            .background(Color(0xFF2D2D2D), RoundedCornerShape(24.dp))
            .align(Alignment.CenterHorizontally)
        ){

            Row {
                Text(
                    text = "몸무게",
                    style = TextStyle(color = Color.White, fontSize = 20.sp),
                    modifier = Modifier
                        .padding(20.dp)
                )

                val weight = viewModel.lastWeightData.observeAsState().value ?:""

                Box(
                    modifier = Modifier
                        .padding(top = 20.dp)
                        .height(48.dp)
                        .padding(start = 10.dp)
                ) {
                    BasicTextField(
                        value = weight,
                        onValueChange = {
                            viewModel.bindTextFieldWeight(it) },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        cursorBrush = SolidColor(Color.White),
                        modifier = Modifier
                            .drawBehind {
                                drawLine(
                                    color = Color.White,
                                    start = Offset(0f, size.height + 8.dp.toPx()),
                                    end = Offset(size.width, size.height+ 8.dp.toPx()),
                                    strokeWidth = 1.dp.toPx()
                                )
                            }
                            .padding(end = 12.dp),
                        textStyle = TextStyle(color = Color.White, fontSize = 20.sp, textAlign = TextAlign.End),
                    )
                }

                Text(
                    text = "kg",
                    style = TextStyle(color = Color.White, fontSize = 20.sp),
                    modifier = Modifier
                        .padding(20.dp)
                )
            }
        }

        for(i in viewModel.toDayExerciseList){
            Box(modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .padding(top = 20.dp)
                .background(Color(0xFF2D2D2D), RoundedCornerShape(24.dp))
                .align(Alignment.CenterHorizontally)
            ){
                Text(
                    text = i.name,
                    color = Color.White
                )
            }
        }

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .padding(top = 30.dp)
                .size(120.dp, 40.dp)
                .clip(CircleShape)
                .background(Color.Transparent)
                .border(3.dp, Color.White, CircleShape)
                .clickable { viewModel.saveExercise() }
        ) {
            Text(
                text = "저장",
                color = Color.White
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ExercisePreview() {
    exerciseView()
}