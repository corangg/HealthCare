package com.example.healthcare.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import com.example.healthcare.VIewModel.MainViewModel
import com.example.healthcare.ViewModelFactory.ViewModelFactory
import com.example.healthcare.ui.theme.HealthCareTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val mainViewModelFactory = ViewModelFactory {
                MainViewModel()
            }
            val mainViewModel = ViewModelProvider(this, mainViewModelFactory).get(MainViewModel::class.java)
            val a = true
            if(a==true){
                startActivity(Intent(this, InformationInputActivity::class.java))
            }

            HealthCareTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MyAppPreview(mainViewModel)
                }
            }
        }
    }
}

@Composable
fun MyAppPreview(viewModel: MainViewModel) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = viewModel.backgroundColor.value),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = {viewModel.onAClicked()}) {
            Text("A")
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
    MyAppPreview(MainViewModel())
}


