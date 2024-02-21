package com.example.healthcare.ui.composable

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.healthcare.R
import com.example.healthcare.VIewModel.InformationInputViewModel
import com.example.healthcare.ui.theme.HealthCareTheme

@Composable
fun InformationInputView(viewModel: InformationInputViewModel) {
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current
    val backgroundClickAction = Modifier.clickable {
        focusManager.clearFocus()
    }

    val heightSliderValue by viewModel.heightValue.observeAsState(0f)
    val heightText = remember { mutableStateOf(heightSliderValue.toString()) }
    val heightSliderMaxValue = 300f

    val weightSliderValue by viewModel.weightValue.observeAsState(0f)
    val weightText = remember { mutableStateOf(weightSliderValue.toString()) }
    val weightSliderMaxValue = 200f


    Box(
        modifier = Modifier
            .fillMaxSize()
            .then(backgroundClickAction) // 배경 클릭 액션을 적용합니다.
            .background(MaterialTheme.colorScheme.background)
    )

    LaunchedEffect(heightSliderValue) {
        heightText.value = String.format("%.1f", heightSliderValue)
    }

    LaunchedEffect(weightSliderValue){
        weightText.value = String.format("%.2f", weightSliderValue)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = viewModel.backgroundColor.value),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "정보 입력",
            style = TextStyle(color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold),
        modifier = Modifier.padding(top = 40.dp)
        )

        Spacer(modifier = Modifier.height(10.dp))

        SliderInputRow(
            iconId = R.drawable.ic_height,
            iconDescription = "Height Slider",
            sliderValue = heightSliderValue,
            onSliderValueChange = {viewModel.setHeightValue(it)},
            sliderMaxValue = heightSliderMaxValue,
            textValue = heightText.value,
            onTextValueChange =
            { val floatValue = it.toFloatOrNull()
                if (floatValue == null){
                    if(it.isEmpty())heightText.value = ""
                }else{
                    if (floatValue <= heightSliderMaxValue){
                        heightText.value = String.format("%.1 f",floatValue)
                        viewModel.setHeightValue(floatValue)
                    }
                }
            },
            unitText = "cm",
            focusRequester = focusRequester
        )

        Spacer(modifier = Modifier.height(16.dp))

        SliderInputRow(
            iconId = R.drawable.ic_height,
            iconDescription = "Height Slider",
            sliderValue = heightSliderValue,
            onSliderValueChange = {viewModel.setHeightValue(it)},
            sliderMaxValue = heightSliderMaxValue,
            textValue = heightText.value,
            onTextValueChange =
            { val floatValue = it.toFloatOrNull()
                if (floatValue == null){
                    if(it.isEmpty())heightText.value = ""
                }else{
                    if (floatValue <= heightSliderMaxValue){
                        heightText.value = String.format("%.1 f",floatValue)
                        viewModel.setHeightValue(floatValue)
                    }
                }
            },
            unitText = "cm",
            focusRequester = focusRequester
        )

        Spacer(modifier = Modifier.height(16.dp))

        SliderInputRow(
            iconId = R.drawable.ic_weight,
            iconDescription = "Weight Slider",
            sliderValue = weightSliderValue,
            onSliderValueChange = {viewModel.setWeightValue(it)},
            sliderMaxValue = weightSliderMaxValue,
            textValue = weightText.value,
            onTextValueChange = { val floatValue = it.toFloatOrNull()
                if (floatValue == null){
                    if(it.isEmpty())weightText.value = ""
                }else{
                    if (floatValue <= weightSliderMaxValue){
                        weightText.value = String.format("%.1 f",floatValue)
                        viewModel.setWeightValue(floatValue)
                    }
                }
            },
            unitText = "kg",
            focusRequester = focusRequester
        )






        Spacer(modifier = Modifier.height(16.dp))


    }
}
@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    HealthCareTheme {
        InformationInputView(InformationInputViewModel())
    }
}