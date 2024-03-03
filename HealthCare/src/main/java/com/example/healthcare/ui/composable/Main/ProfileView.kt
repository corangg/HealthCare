package com.example.healthcare.ui.composable.Main

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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
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
import androidx.compose.ui.platform.LocalContext
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


@Composable
fun ProfileView() {
    val viewModel: MainViewModel = hiltViewModel()
    LaunchedEffect(true) {
        viewModel.getProfile()
    }
    val name = viewModel.profileData.observeAsState().value?.name ?:""
    val gender = viewModel.profileData.observeAsState().value?.gender ?:true
    val age = viewModel.profileData.observeAsState().value?.age ?:0
    val height = viewModel.profileData.observeAsState().value?.height ?:0f
    val weight = viewModel.profileData.observeAsState().value?.weight ?:0f

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = viewModel.backgroundColor.value),
    ){
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

                profileRow(itemImg = R.drawable.ic_age, itemText = "나이", itemDetail = age.toString(),"세", editClicked = {viewModel.editProfile(3)})
                profileRow(itemImg = R.drawable.ic_height, itemText = "키", itemDetail = height.toString(), "cm", editClicked = {viewModel.editProfile(4)})
                profileRow(itemImg = R.drawable.ic_weight, itemText = "체중", itemDetail = weight.toString(),"kg", editClicked = {viewModel.editProfile(5)})
                Spacer(modifier = Modifier.height(48.dp))
            }

        }

        Spacer(modifier = Modifier.height(24.dp))


        Box(modifier = Modifier
            .align(Alignment.BottomCenter)
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
            .background(Color(0xFF2D2D2D), RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
        ){

            when(viewModel.viewEditCompsable.observeAsState().value){
                1-> editPhsicalInfoView("이름",viewModel.profileName,{viewModel.editName()})
                2-> editGenderInfoView("성별",{viewModel.editGender()})
                3-> editPhsicalInfoView("나이",viewModel.profileAge,{viewModel.editAge()})
                4-> editPhsicalInfoView("키",viewModel.profileHeight,{viewModel.editHeight()})
                5-> editPhsicalInfoView("몸무게",viewModel.profileWeight,{viewModel.editWeight()})
            }
        }
    }
}

@Composable
fun profileRow(
    itemImg : Int,
    itemText : String,
    itemDetail : String,
    unit : String,
    editClicked : ()-> Unit) {

    Row(modifier = Modifier.padding(top = 20.dp), verticalAlignment = Alignment.CenterVertically) {

        Image(
            painter = painterResource(id = itemImg),
            contentDescription = null,
            modifier = Modifier
                .size(36.dp)
                .clip(CircleShape)
                .border(border = BorderStroke(3.dp, Color.White), shape = CircleShape)
                .padding(8.dp),
            colorFilter = ColorFilter.tint(Color.White)
        )

        Text(
            text = itemText,
            style = TextStyle(color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold),
            modifier = Modifier
                .padding(start = 16.dp)
                .width(64.dp)
        )

        Text(
            text = itemDetail,
            style = TextStyle(color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold),
            modifier = Modifier
                .padding(start = 10.dp)
                .width(80.dp),
            textAlign = TextAlign.End
        )

        Text(
            text = unit,
            style = TextStyle(color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold),
            modifier = Modifier
                .padding(start = 6.dp)
                .width(32.dp),
            textAlign = TextAlign.End
        )

        Image(
            painter = painterResource(id = R.drawable.ic_right),
            contentDescription = "",
            colorFilter = ColorFilter.tint(Color.White),
            modifier = Modifier
                .size(32.dp)
                .padding(start = 18.dp)
                .clickable(onClick = editClicked))
    }
}

@Composable
fun editPhsicalInfoView(item : String, value : MutableLiveData<*>, editClicked: () -> Unit){
    val viewModel: MainViewModel = hiltViewModel()
    var itemValue by remember { mutableStateOf(value.value.toString()) }

    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = item,
            style = TextStyle(color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp)
                .height(48.dp)
                .border(2.dp, Color.White, RoundedCornerShape(6.dp))
                .padding(horizontal = 10.dp)
        ) {
            BasicTextField(
                value = itemValue,
                onValueChange = {
                    itemValue = it
                    value.value = it},
                singleLine = true,
                cursorBrush = SolidColor(Color.White),
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterStart),
                textStyle = TextStyle(color = Color.White, fontSize = 16.sp)
            )
        }

        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier
            .fillMaxWidth()
            .padding(top = 20.dp)) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .weight(1f)
                    .height(40.dp)
                    .background(Color.Transparent)
                    .border(3.dp, Color.White, RoundedCornerShape(12.dp))
                    .clickable { viewModel.editCancel() }
            ) {
                Text(
                    text = "취소",
                    color = Color.White
                )
            }

            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .weight(1f)
                    .height(40.dp)
                    .background(Color.Transparent)
                    .border(3.dp, Color.White, RoundedCornerShape(12.dp))
                    .clickable {
                        editClicked()
                    }
            ) {
                Text(
                    text = "저장",
                    color = Color.White
                )
            }
        }

        Spacer(modifier = Modifier.height(80.dp))
    }

}


@Composable
fun editGenderInfoView(item : String, editClicked: () -> Unit){
    val viewModel: MainViewModel = hiltViewModel()
    val genderInfo by viewModel.profileGender.observeAsState()

    val maleButtonBackgroundColor = if (genderInfo == true){
        Color.LightGray
    } else Color.Transparent

    val femaleButtonBackgroundColor = if (genderInfo == false){
        Color.LightGray
    } else Color.Transparent

    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = item,
            style = TextStyle(color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold))

        Row(modifier = Modifier.padding(top = 20.dp)) {
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
        }

        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier
            .fillMaxWidth()
            .padding(top = 20.dp)) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .weight(1f)
                    .height(40.dp)
                    .background(Color.Transparent)
                    .border(3.dp, Color.White, RoundedCornerShape(12.dp))
                    .clickable { viewModel.editCancel() }
            ) {
                Text(
                    text = "취소",
                    color = Color.White
                )
            }

            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .weight(1f)
                    .height(40.dp)
                    .background(Color.Transparent)
                    .border(3.dp, Color.White, RoundedCornerShape(12.dp))
                    .clickable {
                        editClicked()
                    }
            ) {
                Text(
                    text = "저장",
                    color = Color.White
                )
            }
        }

        Spacer(modifier = Modifier.height(80.dp))
    }

}


@Preview(showBackground = true)
@Composable
fun ProfilePreview() {
    val viewModel: MainViewModel = hiltViewModel()
    ProfileView()
}