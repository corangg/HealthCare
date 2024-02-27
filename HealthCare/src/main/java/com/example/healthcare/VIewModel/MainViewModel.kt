package com.example.healthcare.VIewModel

import android.app.Application
import androidx.compose.material.SnackbarDefaults.backgroundColor
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(): ViewModel() {
    var backgroundColor = mutableStateOf(Color(0xFF121212))
        private set

    fun onAClicked() {
        backgroundColor.value = Color.Red
    }

    fun onBClicked() {
        backgroundColor.value = Color.Blue
    }
}
