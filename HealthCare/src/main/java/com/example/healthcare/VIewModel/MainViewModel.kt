package com.example.healthcare.VIewModel

import android.app.Application
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {
    var backgroundColor = mutableStateOf(Color(0xFF182120))
        private set

    fun onAClicked() {
        backgroundColor.value = Color.Red
    }

    fun onBClicked() {
        backgroundColor.value = Color.Blue
    }
}