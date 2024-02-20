@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.healthcare.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import com.example.healthcare.VIewModel.InformationInputViewModel
import com.example.healthcare.ViewModelFactory.ViewModelFactory
import com.example.healthcare.ui.theme.HealthCareTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import com.example.healthcare.R
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.*
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.input.key.Key.Companion.K
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction





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
    val sliderValue by viewModel.heightValue.observeAsState(0f)
    val text = remember { mutableStateOf(sliderValue.toString()) }
    val sliderMaxValue = 300f
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current
    val backgroundClickAction = Modifier.clickable {
        focusManager.clearFocus()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .then(backgroundClickAction) // 배경 클릭 액션을 적용합니다.
            .background(MaterialTheme.colorScheme.background)
    )

    LaunchedEffect(sliderValue) {
        text.value = String.format("%.1f", sliderValue)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = viewModel.backgroundColor.value),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("정보 입력", style = TextStyle(color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold))
        Spacer(modifier = Modifier.height(8.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(id = R.drawable.ic_height),
                contentDescription = "Slider Image",
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .border(border = BorderStroke(3.dp, Color.White), shape = CircleShape)
                    .padding(6.dp),
                colorFilter = ColorFilter.tint(Color.White)
            )
            Slider(
                value = sliderValue,
                onValueChange = { value ->
                    viewModel.setHeightValue(value)
                },
                valueRange = 0f..sliderMaxValue,
                colors = SliderDefaults.colors(
                    activeTrackColor = Color.White,
                    inactiveTrackColor = Color.Gray,
                    thumbColor = Color.White
                ),
                modifier = Modifier
                    .width(160.dp)
                    .padding(start = 10.dp)
            )

            BasicTextField(
                value = text.value,
                onValueChange = { newValue ->
                    val floatValue = newValue.toFloatOrNull()
                    if (floatValue == null) {
                        if (newValue.isEmpty()) text.value = ""
                    } else {
                        if (floatValue <= sliderMaxValue) {
                            text.value = String.format("%.1f", floatValue)
                            viewModel.setHeightValue(floatValue)
                        }
                    }
                },
                textStyle = TextStyle(color = Color.White, fontSize = 16.sp),
                singleLine = true,
                cursorBrush = SolidColor(Color.White),
                modifier = Modifier
                    .background(Color(0xFF2D2D2D), RoundedCornerShape(8.dp))
                    .padding(horizontal = 10.dp, vertical = 8.dp)
                    .width(42.dp)
                    .focusRequester(focusRequester)
                    .onFocusChanged { focusState ->
                        if (focusState.isFocused && text.value == "0.0") {
                            text.value = ""
                        } else if (!focusState.isFocused && text.value.isEmpty()) {
                            text.value = "0.0"
                        }
                                    },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
            )
            Text(
                text = "cm",
                modifier = Modifier.padding(start = 10.dp),
                color = Color.White,
                fontSize = 22.sp
            )
        }
    }
}
@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    HealthCareTheme {
        InformationInputView(InformationInputViewModel())
    }
}