package com.example.healthcare.ui.composable.InformationInput

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
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.healthcare.R
import com.example.healthcare.VIewModel.InformationInputViewModel
import com.example.healthcare.VIewModel.MainViewModel
import com.example.healthcare.ui.composable.Common.AddExerciseColumn
import com.example.healthcare.ui.composable.Common.HorizontalScrollView


@Composable
fun InformationInputView(viewModel: InformationInputViewModel) {
    val context = LocalContext.current//뷰모델 수정하면 필요없을거임
    val focusRequester = remember { FocusRequester() }

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


    LaunchedEffect(heightSliderValue) {
        heightText.value = String.format("%.1f", heightSliderValue)
    }

    LaunchedEffect(weightSliderValue){
        weightText.value = String.format("%.2f", weightSliderValue)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xFF121212)),
        horizontalAlignment = Alignment.CenterHorizontally) {

        ProfileInputView(viewModel = viewModel, focusRequester = focusRequester)

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
            for(i in 0 until 7){
                AddExerciseColumn(
                    day = i,
                    list = viewModel.exerciseLists[i],
                    onDeleteClicked = viewModel::deleteExercise,
                    onAddClicked = viewModel::addExercise,
                    onUpdate = viewModel::updateExerciseAt,
                    edit = true)
            }
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

@Composable
fun ProfileInputView(viewModel: InformationInputViewModel, focusRequester: FocusRequester){
    val genderInfo by viewModel.genderInfo.observeAsState()

    Column(
        horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = "신체 정보 입력",
            style = TextStyle(color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold),
            modifier = Modifier.padding(top = 40.dp))

        InformationInputNameRow(viewModel::setNameValue, focusRequester)

        Spacer(modifier = Modifier.height(16.dp))

        InformationInputGenderRow(genderInfo = genderInfo, selectGender = viewModel::selectGender)


    }
}

@Composable
fun InformationInputNameRow(valueChange: (String)->Unit, focusRequester: FocusRequester){
    var nameText by remember { mutableStateOf("이름") }

    Row(verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(top = 24.dp)) {
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

        InformationInputTextField(value = nameText, modifier = Modifier.width(80.dp), valueChange = valueChange, focusRequester = focusRequester)
    }
}

@Composable
fun InformationInputGenderRow(genderInfo: Boolean?, selectGender: (Boolean)->Unit){
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
                .background(if(genderInfo==true)Color.LightGray else Color.Transparent)
                .border(3.dp, Color.White, CircleShape)
                .clickable { selectGender(true) }
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
                .background(if(genderInfo==false)Color.LightGray else Color.Transparent)
                .border(3.dp, Color.White, CircleShape)
                .clickable { selectGender(false) }
        ) {
            Text(
                text = "여성",
                color = Color.White
            )
        }
        Spacer(modifier = Modifier.width(78.dp))

    }
}
