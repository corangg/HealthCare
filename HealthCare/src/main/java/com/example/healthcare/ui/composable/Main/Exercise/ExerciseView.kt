package com.example.healthcare.ui.composable.Main.Exercise

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
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
        viewModel.getCurrentDayOfWeek()
    }
    //val arrayDayOfTheWeek = arrayOf("일","월","화","수","목","금","토")
    //val dayOfTheWeek = arrayDayOfTheWeek[viewModel.getCurrentDayOfWeek()-1]
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
                    .clickable{viewModel.minusDate()}
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
    }
}

@Preview(showBackground = true)
@Composable
fun ExercisePreview() {
    exerciseView()
}