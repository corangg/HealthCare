package com.example.healthcare.ui.composable.Main.Exercise

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.healthcare.VIewModel.MainViewModel

@Composable
fun exerciseView(){

    val viewModel: MainViewModel = hiltViewModel()
    LaunchedEffect(true) {
        viewModel.getDataBase()
    }
    val arrayDayOfTheWeek = arrayOf("일","월","화","수","목","금","토")
    val dayOfTheWeek = arrayDayOfTheWeek[viewModel.getCurrentDayOfWeek()-1]


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = viewModel.backgroundColor.value),
    ){
        Text(
            text = "$dayOfTheWeek 요일",
            style = TextStyle(color = Color.White, fontSize = 24.sp, fontWeight = FontWeight.Bold),
            modifier = Modifier
                .padding(top = 20.dp)

        )
    }
}