package com.example.healthcare.ui.composable

import android.widget.HorizontalScrollView
import android.widget.ScrollView
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.healthcare.R
import com.example.healthcare.VIewModel.InformationInputViewModel
import com.example.healthcare.ui.theme.HealthCareTheme
import kotlin.math.min

@Composable
fun InformationInputView(viewModel: InformationInputViewModel) {
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current
    val backgroundClickAction = Modifier.clickable {
        focusManager.clearFocus()
    }

    val ageSliderValue by viewModel.ageValue.observeAsState(0f)
    val ageText = remember { mutableStateOf(ageSliderValue.toString()) }
    val ageSliderMaxValue = 100f
    val ageIntValue = ageSliderValue.toInt()
    val ageIntTextValue = ageIntValue.toString()

    val heightSliderValue by viewModel.heightValue.observeAsState(0f)
    val heightText = remember { mutableStateOf(heightSliderValue.toString()) }
    val heightSliderMaxValue = 300f

    val weightSliderValue by viewModel.weightValue.observeAsState(0f)
    val weightText = remember { mutableStateOf(weightSliderValue.toString()) }
    val weightSliderMaxValue = 200f

    val genderInfo by viewModel.genderInfo.observeAsState()

    val maleButtonBackgroundColor = if (genderInfo == true){
        Color.LightGray
    } else Color.Transparent

    val femaleButtonBackgroundColor = if (genderInfo == false){
        Color.LightGray
    } else Color.Transparent




    Box(
        modifier = Modifier
            .fillMaxSize()
            .then(backgroundClickAction)
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
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = "신체 정보 입력",
            style = TextStyle(color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold),
        modifier = Modifier.padding(top = 40.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically){
            Image(
                painter = painterResource(id = R.drawable.ic_gender),
                contentDescription = "Gender",
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .border(border = BorderStroke(3.dp, Color.White), shape = CircleShape)
                    .padding(8.dp),
                colorFilter = ColorFilter.tint(Color.White)
            )

            Spacer(modifier = Modifier.width(44.dp))

            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(70.dp, 40.dp)
                    .clip(CircleShape)
                    .background(maleButtonBackgroundColor)
                    .border(3.dp, Color.White, CircleShape)
                    .clickable { viewModel.selectGender(true) }
            ) {
                Text(
                    text = "남성",
                    color = Color.White
                )
            }

            Spacer(modifier = Modifier.width(24.dp))

            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(70.dp, 40.dp)
                    .clip(CircleShape)
                    .background(femaleButtonBackgroundColor)
                    .border(3.dp, Color.White, CircleShape)
                    .clickable { viewModel.selectGender(false) }
            ) {
                Text(
                    text = "여성",
                    color = Color.White
                )
            }
            Spacer(modifier = Modifier.width(78.dp))

        }

        Spacer(modifier = Modifier.height(16.dp))

        SliderInputRow(
            iconId = R.drawable.ic_age,
            iconDescription = "Age Slider",
            sliderValue = ageIntValue.toFloat(),
            onSliderValueChange = {viewModel.setAgeValue(it.toInt().toFloat())},
            sliderMaxValue = ageSliderMaxValue,
            textValue = ageIntTextValue,
            onTextValueChange =
            {val intValue = it.toIntOrNull()
                if(intValue == null){
                    if(it.isEmpty()){
                        ageText.value = ""
                    }
                }else{
                    if(intValue <= ageSliderMaxValue){
                        viewModel.setAgeValue(it.toFloat())
                    }
                }
            },
            unitText = "세",
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
                    if(it.isEmpty()) {
                        heightText.value = ""
                    }
                }else{
                    if (floatValue <= heightSliderMaxValue){
                        heightText.value = String.format("%.1f",floatValue)
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
                    if(it.isEmpty()) {
                        weightText.value = ""
                    }
                }else{
                    if (floatValue <= weightSliderMaxValue){
                        weightText.value = String.format("%.1f",floatValue)
                        viewModel.setWeightValue(floatValue)
                    }
                }
            },
            unitText = "kg",
            focusRequester = focusRequester
        )

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = "주간 운동 루틴",
            style = TextStyle(color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold),
        )

        Spacer(modifier = Modifier.height(16.dp))

        HorizontalScrollView(modifier = Modifier.widthIn(86.dp)) {
            AddExerciseColumn(day = 0, viewModel, viewModel.sunExerciseList)
            AddExerciseColumn(day = 1, viewModel, viewModel.monExerciseList)
            AddExerciseColumn(day = 2, viewModel, viewModel.tuesExerciseList)
            AddExerciseColumn(day = 3, viewModel, viewModel.wednesExerciseList)
            AddExerciseColumn(day = 4, viewModel, viewModel.thursExerciseList)
            AddExerciseColumn(day = 5, viewModel, viewModel.friExerciseList)
            AddExerciseColumn(day = 6, viewModel, viewModel.saturExerciseList)
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