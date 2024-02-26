package com.example.healthcare.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.healthcare.VIewModel.InformationInputViewModel
import com.example.healthcare.VIewModel.MainViewModel
import com.example.healthcare.ViewModelFactory.ViewModelFactory
import com.example.healthcare.ui.theme.HealthCareTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel : MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val a = true
            if(a==true){
                startActivity(Intent(this, InformationInputActivity::class.java))
            }

            HealthCareTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MyAppPreview()
                }
            }
        }
    }
}

@Composable
fun MyAppPreview() {
    val viewModel: MainViewModel = hiltViewModel()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = viewModel.backgroundColor.value),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            onClick = {viewModel.onBClicked()},
            modifier = Modifier
                .size(60.dp,20.dp)
                .padding(0.dp)
                .border(3.dp, Color.White, shape = CircleShape)
                .background(color = Color.Transparent, shape = RoundedCornerShape(4.dp)),
        ) {
            Text(
                text = "남성",
                color = Color.White
            )

        }
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = {viewModel.onBClicked()}) {
            Text("B")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MyAppPreview()
}


