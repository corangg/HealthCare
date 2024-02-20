package com.example.healthcare.VIewModel

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel

class InformationInputViewModel: ViewModel() {
    var backgroundColor = mutableStateOf(Color(0xFF182120))
        private set
}