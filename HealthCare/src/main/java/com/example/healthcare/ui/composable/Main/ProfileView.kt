package com.example.healthcare.ui.composable.Main

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.healthcare.VIewModel.InformationInputViewModel
import com.example.healthcare.VIewModel.MainViewModel


@Composable
fun ProfileView(/*viewModel: MainViewModel*/) {
    val viewModel: MainViewModel = hiltViewModel()
    val context = LocalContext.current
    LaunchedEffect(true) {
        viewModel.getName(context)
    }
    val name = viewModel.profileName.observeAsState("").value

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = viewModel.backgroundColor.value),
        horizontalAlignment = Alignment.CenterHorizontally,
    ){

        Box(modifier = Modifier
            .fillMaxWidth()
            .height(400.dp)
            .padding(20.dp)
            .background(Color(0xFF2D2D2D), RoundedCornerShape(24.dp))
            ){
            Row() {
                Text(
                    text = "이름 : ",
                    style = TextStyle(color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold),
                    modifier = Modifier.padding(top = 40.dp)
                )

                Spacer(modifier = Modifier.width(20.dp))

                Text(
                    text = name,
                    style = TextStyle(color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold),
                    modifier = Modifier.padding(top = 40.dp)
                )
            }
        }




        Spacer(modifier = Modifier.height(24.dp))
    }

}

@Preview(showBackground = true)
@Composable
fun ProfilePreview() {
    val viewModel: MainViewModel = hiltViewModel()
    ProfileView()
}