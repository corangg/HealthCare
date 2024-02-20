package com.example.healthcare.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import com.example.healthcare.VIewModel.InformationInputViewModel
import com.example.healthcare.VIewModel.MainViewModel
import com.example.healthcare.ViewModelFactory.ViewModelFactory
import com.example.healthcare.ui.theme.HealthCareTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

class InformationInputActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val viewModelFactory = ViewModelFactory {InformationInputViewModel()}
            val viewModel = ViewModelProvider(this, viewModelFactory).get(InformationInputViewModel::class.java)
            HealthCareTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    InformationInputView(viewModel)
                }
            }
        }
    }
}

@Composable
fun InformationInputView(viewModel: InformationInputViewModel) {
    var sliderValue by remember { mutableStateOf(0f) } // 슬라이더의 값 상태를 관리
    val sliderValueText = sliderValue.toString() // 슬라이더 값의 문자열 표현


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = viewModel.backgroundColor.value),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("정보 입력", style = TextStyle(color = Color.White,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold))

        Spacer(modifier = Modifier.height(8.dp)) // 텍스트와 슬라이더 사이의 여백
        Slider(
            value = sliderValue,
            onValueChange = { sliderValue = it },
            valueRange = 0f..100f // 슬라이더 값의 범위
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    HealthCareTheme {
        InformationInputView(InformationInputViewModel())
    }
}