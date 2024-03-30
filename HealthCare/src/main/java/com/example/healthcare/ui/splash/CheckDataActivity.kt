package com.example.healthcare.ui.splash

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.example.healthcare.VIewModel.InformationInputViewModel
import com.example.healthcare.ui.InformationInputActivity
import com.example.healthcare.ui.MainActivity
import com.example.healthcare.ui.theme.HealthCareTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashActivity : ComponentActivity() {
    private val viewModel : InformationInputViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.checkProfileData(this)
        setContent {
            HealthCareTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {}
            }
        }
        setObserve()
    }
    fun setObserve(){
        viewModel.checkProfile.observe(this){
            if(it){
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }else{
                startActivity(Intent(this, InformationInputActivity::class.java))
                finish()
            }
        }
    }
}


@Composable
fun SplashView() {
    Column(modifier = Modifier
        .fillMaxWidth()
        .background(color = Color.Black)) {
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview2() {
    HealthCareTheme {
        SplashView()
    }
}