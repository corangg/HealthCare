@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.healthcare.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.healthcare.VIewModel.InformationInputViewModel
import com.example.healthcare.ui.theme.HealthCareTheme
//import com.example.healthcare.ui.composable.InformationInputView
import android.widget.Toast
import androidx.activity.viewModels
import com.example.healthcare.ui.composable.InformationInput.InformationInputView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class InformationInputActivity : ComponentActivity() {
    private val viewModel : InformationInputViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HealthCareTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    InformationInputView(viewModel)
                }
            }
        }
        setObserve(viewModel)
    }

    fun setObserve(viewModel: InformationInputViewModel){
        viewModel.dataSaveFail.observe(this){
            when(it){
                1 ->  Toast.makeText(this,"이름을 입력해 주세요.", Toast.LENGTH_SHORT).show()
                2 ->  Toast.makeText(this,"성별을 입력해 주세요.", Toast.LENGTH_SHORT).show()
                3 ->  Toast.makeText(this,"나이를 입력해 주세요.", Toast.LENGTH_SHORT).show()
                4 ->  Toast.makeText(this,"키를 입력해 주세요.", Toast.LENGTH_SHORT).show()
                5 ->  Toast.makeText(this,"몸무게를 입력해 주세요.", Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.dataSaveSuccess.observe(this){
            finish()
            startActivity(Intent(this, MainActivity::class.java))
        }
    }
}