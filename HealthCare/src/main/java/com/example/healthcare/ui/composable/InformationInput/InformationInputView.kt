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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.healthcare.R
import com.example.healthcare.VIewModel.InformationInputViewModel
import com.example.healthcare.VIewModel.MainViewModel
import com.example.healthcare.ui.composable.Common.AddExerciseColumn
import com.example.healthcare.ui.composable.Common.HorizontalScrollView

@Composable
fun InformationInputView(viewModel: InformationInputViewModel) {
    val focusRequester = remember { FocusRequester() }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xFF121212)),
        horizontalAlignment = Alignment.CenterHorizontally) {

        ProfileInputView(viewModel = viewModel, focusRequester = focusRequester)

        Spacer(modifier = Modifier.height(32.dp))

        ExerciseRoutineInputView(viewModel)

        Spacer(modifier = Modifier.height(48.dp))

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(120.dp, 60.dp)
                .clip(CircleShape)
                .background(Color.Transparent)
                .border(3.dp, Color.White, CircleShape)
                .clickable { viewModel.saveData() }
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
    val ageSliderValue by viewModel.ageValue.observeAsState(0f)
    val heightSliderValue by viewModel.heightValue.observeAsState(0f)
    val weightSliderValue by viewModel.weightValue.observeAsState(0f)


    Column(
        horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = "신체 정보 입력",
            style = TextStyle(color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold),
            modifier = Modifier.padding(top = 40.dp))

        Spacer(modifier = Modifier.height(24.dp))

        InformationInputNameRow(viewModel::setNameValue, focusRequester)

        Spacer(modifier = Modifier.height(16.dp))

        InformationInputGenderRow(genderInfo = genderInfo, selectGender = viewModel::selectGender)

        Spacer(modifier = Modifier.height(16.dp))

        SliderInputRow(
            iconId = R.drawable.ic_age,
            sliderValue = ageSliderValue,
            onSliderValueChange = viewModel::setAgeValue,
            sliderMaxValue = 100f,
            onTextValueChange = viewModel::setAgeValue,
            unitText = "세",
            textFiledvariable = Int,
            focusRequester = focusRequester
        )

        Spacer(modifier = Modifier.height(16.dp))

        SliderInputRow(
            iconId = R.drawable.ic_height,
            sliderValue = heightSliderValue,
            onSliderValueChange = {viewModel.setHeightValue(it)},
            sliderMaxValue = 300f,
            onTextValueChange = viewModel::setHeightValue,
            unitText = "cm",
            textFiledvariable = Float,
            focusRequester = focusRequester
        )

        Spacer(modifier = Modifier.height(16.dp))

        SliderInputRow(
            iconId = R.drawable.ic_weight,
            sliderValue = weightSliderValue,
            onSliderValueChange = {viewModel.setWeightValue(it)},
            sliderMaxValue = 200f,
            onTextValueChange = viewModel::setWeightValue,
            unitText = "kg",
            textFiledvariable = Float,
            focusRequester = focusRequester
        )
    }
}

@Composable
fun ExerciseRoutineInputView(viewModel: InformationInputViewModel){
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
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
    }
}

@Composable
fun InformationInputNameRow(valueChange: (String)->Unit, focusRequester: FocusRequester){
    var nameText by remember { mutableStateOf("이름") }

    Row(verticalAlignment = Alignment.CenterVertically) {
        Image(
            painter = painterResource(id = R.drawable.ic_name),
            contentDescription = null,
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .border(border = BorderStroke(3.dp, Color.White), shape = CircleShape)
                .padding(8.dp),
            colorFilter = ColorFilter.tint(Color.White)
        )
        Spacer(modifier = Modifier.width(160.dp))
        InformationInputTextField(value = nameText, modifier = Modifier.width(80.dp), valueChange = valueChange, focusRequester = focusRequester)

        Spacer(modifier = Modifier.width(46.dp))
    }
}

@Composable
fun InformationInputGenderRow(genderInfo: Boolean?, selectGender: (Boolean)->Unit){
    Row(verticalAlignment = Alignment.CenterVertically){
        Image(
            painter = painterResource(id = R.drawable.ic_gender),
            contentDescription = null,
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
                .background(if (genderInfo == true) Color.LightGray else Color.Transparent)
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
                .background(if (genderInfo == false) Color.LightGray else Color.Transparent)
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
