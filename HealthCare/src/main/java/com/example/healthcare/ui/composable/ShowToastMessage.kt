package com.example.healthcare.ui.composable

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.healthcare.ui.composable.Main.MyAppPreview
import kotlinx.coroutines.delay


@Composable
fun ShowToastMessage(
    message: String,
    modifier: Modifier = Modifier
        .padding(bottom = 20.dp)
        .background(Color.Transparent)
        .border(2.dp, Color.White)
        .padding(horizontal = 20.dp, vertical = 10.dp),
    textStyle: TextStyle = TextStyle(color = Color.White)
    ){
    Box(modifier = modifier){
        Text(
            text = message,
            style = textStyle
        )
    }
}
