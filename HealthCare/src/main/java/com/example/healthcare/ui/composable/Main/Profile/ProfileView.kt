package com.example.healthcare.ui.composable.Main.Profile

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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.MutableLiveData
import com.example.healthcare.R
import com.example.healthcare.VIewModel.MainViewModel
import com.example.healthcare.ui.composable.AddExerciseColumn
import com.example.healthcare.ui.composable.HorizontalScrollView


@Composable
fun ProfileView(viewModel: MainViewModel) {
    val name = viewModel.profileName.observeAsState().value ?:""
    val gender = viewModel.profileGender.observeAsState().value ?:true
    val age = viewModel.profileAge.observeAsState().value ?:""
    val height = viewModel.profileHeight.observeAsState().value ?:""
    val weight = viewModel.profileWeight.observeAsState().value ?:""

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = viewModel.backgroundColor.value),
    ){
        val scrollState = rememberScrollState()
        Column(modifier = Modifier.fillMaxWidth().verticalScroll(scrollState), horizontalAlignment = Alignment.CenterHorizontally) {
            Box(modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
                .background(Color(0xFF2D2D2D), RoundedCornerShape(24.dp))
            ){
                Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "신체 정보",
                        style = TextStyle(color = Color.White, fontSize = 24.sp, fontWeight = FontWeight.Bold),
                        modifier = Modifier
                            .padding(top = 20.dp)
                            .align(Alignment.CenterHorizontally),
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    profileRow(itemImg = R.drawable.ic_name, itemText = "이름", itemDetail = name,"", editClicked = {viewModel.editProfile(1)})
                    if(gender){
                        profileRow(itemImg = R.drawable.ic_gender, itemText = "성별", itemDetail = "남성","", editClicked = {viewModel.editProfile(2)})
                    }else{
                        profileRow(itemImg = R.drawable.ic_gender, itemText = "성별", itemDetail = "여성", "", editClicked = {viewModel.editProfile(2)})
                    }
                    profileRow(itemImg = R.drawable.ic_age, itemText = "나이", itemDetail = age,"세", editClicked = {viewModel.editProfile(3)})
                    profileRow(itemImg = R.drawable.ic_height, itemText = "키", itemDetail = height, "cm", editClicked = {viewModel.editProfile(4)})
                    profileRow(itemImg = R.drawable.ic_weight, itemText = "체중", itemDetail = weight,"kg", editClicked = {viewModel.editProfile(5)})
                    Spacer(modifier = Modifier.height(48.dp))
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
                    .background(Color(0xFF2D2D2D),RoundedCornerShape(24.dp))
                , horizontalAlignment = Alignment.CenterHorizontally)
            {
                Row(modifier = Modifier.padding(top = 20.dp)) {
                    Text(
                        text = "주간 운동 루틴",
                        style = TextStyle(color = Color.White, fontSize = 24.sp, fontWeight = FontWeight.Bold),
                    )
                    Row() {
                        Image(
                            painter = painterResource(id = R.drawable.ic_right),
                            contentDescription = "",
                            colorFilter = ColorFilter.tint(Color.White),
                            modifier = Modifier
                                .size(32.dp)
                                .padding(start = 18.dp)
                                .clickable(onClick = { viewModel.editExerciseItem() }))
                    }
                }

                Box(modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp)
                ){
                    HorizontalScrollView(
                        modifier = Modifier
                            .padding(20.dp)
                            .widthIn(86.dp)) {
                        for(i in 0 until 7){
                            AddExerciseColumn(
                                day = i,
                                list = viewModel.exerciseLists[i],
                                onDeleteClicked = viewModel::deleteExerciseRoutine,
                                onAddClicked = viewModel::addExerciseRoutine,
                                onUpdate = viewModel::updateExerciseRoutine,
                                edit = viewModel.editExerciseItem)
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(60.dp))
        }

        Box(modifier = Modifier
            .align(Alignment.BottomCenter)
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
            .border(2.dp, Color.White, RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
            .background(Color(0xFF2D2D2D), RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
        ){
            when(viewModel.viewEditCompsable.observeAsState().value){
                1-> editPhsicalInfoView(viewModel = viewModel,"이름",viewModel.profileName,{viewModel.editName()})
                2-> editGenderInfoView(item = "성별", editClicked = {viewModel.editGender()}, viewModel = viewModel)
                3-> editPhsicalInfoView(viewModel = viewModel,"나이",viewModel.profileAge,{viewModel.editAge()})
                4-> editPhsicalInfoView(viewModel = viewModel,"키",viewModel.profileHeight,{viewModel.editHeight()})
                5-> editPhsicalInfoView(viewModel = viewModel,"몸무게",viewModel.profileWeight,{viewModel.editWeight()})
            }
        }
    }
}
