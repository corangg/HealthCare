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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.MutableLiveData
import com.example.healthcare.VIewModel.MainViewModel

@Composable
fun editPhsicalInfoView(viewModel: MainViewModel,item : String, value : MutableLiveData<*>, editClicked: () -> Unit){
    var itemValue by remember { mutableStateOf(value.value.toString()) }

    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = item,
            style = TextStyle(color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold)
        )

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