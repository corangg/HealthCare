package com.example.healthcare.ui.composable.Main.Profile

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.healthcare.VIewModel.MainViewModel

@Composable
fun editGenderInfoView(item : String, editClicked: () -> Unit, viewModel: MainViewModel){
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
            style = TextStyle(color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold)
        )

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