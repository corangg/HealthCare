package com.example.healthcare.ui.composable

import android.content.Context
import android.widget.HorizontalScrollView
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalContext
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
    //val viewModel: InformationInputViewModel = hiltViewModel()
    val context = LocalContext.current
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current
    val backgroundClickAction = Modifier.clickable {
        focusManager.clearFocus()
    }

    var nameText by remember { mutableStateOf("이름") }
    var isFocused by remember { mutableStateOf(false) }

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


        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(id = R.drawable.ic_name),
                contentDescription = "name",
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .border(border = BorderStroke(3.dp, Color.White), shape = CircleShape)
                    .padding(8.dp),
                colorFilter = ColorFilter.tint(Color.White)
            )
            Spacer(modifier = Modifier.width(130.dp))

            BasicTextField(
                value = nameText/*if (isFocused) "" else nameText*/,
                onValueChange = {
                    nameText = it
                    viewModel.name.value= it
                },
                textStyle = TextStyle(color = Color.White, fontSize = 16.sp),
                singleLine = true,
                cursorBrush = SolidColor(Color.White),
                modifier = Modifier
                    .padding(start = 10.dp)
                    .background(Color(0xFF2D2D2D), RoundedCornerShape(8.dp))
                    .padding(horizontal = 10.dp, vertical = 8.dp)
                    .width(80.dp)
                    .focusRequester(focusRequester)
                    .onFocusChanged {
                        isFocused = it.isFocused
                        if(!it.isFocused && nameText.isEmpty()){
                            nameText = "이름"
                        }else if(it.isFocused && nameText == "이름"){
                            nameText = ""
                        }
                    })

            Spacer(modifier = Modifier.width(44.dp))
        }

        Spacer(modifier = Modifier.height(16.dp))


        Row(verticalAlignment = Alignment.CenterVertically){
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
            AddExerciseColumn(viewModel, day = 0, viewModel.sunExerciseList)
            AddExerciseColumn(viewModel, day = 1, viewModel.monExerciseList)
            AddExerciseColumn(viewModel, day = 2, viewModel.tuesExerciseList)
            AddExerciseColumn(viewModel, day = 3, viewModel.wednesExerciseList)
            AddExerciseColumn(viewModel, day = 4, viewModel.thursExerciseList)
            AddExerciseColumn(viewModel, day = 5, viewModel.friExerciseList)
            AddExerciseColumn(viewModel, day = 6, viewModel.saturExerciseList)
        }


        Spacer(modifier = Modifier.height(48.dp))


        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(120.dp, 60.dp)
                .clip(CircleShape)
                .background(Color.Transparent)
                .border(3.dp, Color.White, CircleShape)
                .clickable { viewModel.saveData(context) }
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
fun GreetingPreview() {
    HealthCareTheme {
        InformationInputView(InformationInputViewModel())
    }
}
