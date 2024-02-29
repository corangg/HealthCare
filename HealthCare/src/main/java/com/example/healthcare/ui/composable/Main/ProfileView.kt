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
import com.example.healthcare.R
import com.example.healthcare.VIewModel.MainViewModel


@Composable
fun ProfileView(/*viewModel: MainViewModel*/) {
    val viewModel: MainViewModel = hiltViewModel()
    val context = LocalContext.current
    LaunchedEffect(true) {
        viewModel.getProfile(context)
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
        //horizontalAlignment = Alignment.CenterHorizontally,
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
                0->editNameView("이름",name)
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
fun editNameView(item : String, value : Any/*, onSave : ()->Unit, onCancel : ()-> Unit*/){
    //val itemValue = remember { mutableStateOf(value) }
    var itemValue by remember { mutableStateOf(/*value*/"이강현") }
    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = item,
            style = TextStyle(color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold))

        /*Row(verticalAlignment = (Alignment.CenterVertically)) {
            Box(modifier = Modifier.align(Alignment)) {

            }

        }*/

        /*Row(verticalAlignment = (Alignment.CenterVertically)) {
            BasicTextField(
                value = itemValue.toString(),
                onValueChange = {
                    itemValue = it},
                singleLine = true,
                cursorBrush = SolidColor(Color.White),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .padding(top = 20.dp)
                    .border(2.dp, Color.White, RoundedCornerShape(6.dp))
                    .padding(start = 10.dp)
                    .align(Alignment.CenterVertically)
                ,
                textStyle = TextStyle(color = Color.White, fontSize = 16.sp)
            )

        }*/

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp)
                .height(48.dp)
                .border(2.dp, Color.White, RoundedCornerShape(6.dp))
                .padding(horizontal = 10.dp) // Box의 상하단 패딩을 조정하여 BasicTextField의 위치를 수직 중앙에 오도록 함
        ) {

            BasicTextField(
                value = itemValue,
                onValueChange = { itemValue = it },
                singleLine = true,
                cursorBrush = SolidColor(Color.White),
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterStart), // Box 내에서 BasicTextField를 수직 중앙(정렬 시작점)에 위치시킴
                textStyle = TextStyle(color = Color.White, fontSize = 16.sp)
            )
        }




        Spacer(modifier = Modifier.height(200.dp))
        //BasicTextField(value = , onValueChange = )

        //Spacer(modifier = )
        
    }

}


@Preview(showBackground = true)
@Composable
fun ProfilePreview() {
    val viewModel: MainViewModel = hiltViewModel()
    ProfileView()
}