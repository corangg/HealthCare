package com.example.healthcare.ui.composable.InformationInput

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.healthcare.VIewModel.InformationInputViewModel

@Composable
fun InformationInputTextField(
    value: String,
    modifier: Modifier,
    valueChange: (String)->Unit,
    textStyle: TextStyle = TextStyle(color = Color.White, fontSize = 16.sp),
    singleLine: Boolean = true,
    cursorBrush: SolidColor = SolidColor(Color.White),
    focusRequester: FocusRequester,

    ){
    var textValue = remember { mutableStateOf(value) }
    val isFocused = remember { mutableStateOf(false) }

    BasicTextField(
        value = textValue.value,
        onValueChange = {
            textValue.value = it
            valueChange(it) },
        textStyle = textStyle,
        singleLine = singleLine,
        cursorBrush = cursorBrush,
        modifier = modifier
            .padding(start = 10.dp)
            .background(Color(0xFF2D2D2D), RoundedCornerShape(8.dp))
            .padding(horizontal = 10.dp, vertical = 8.dp)
            .focusRequester(focusRequester)
            .onFocusChanged {
                isFocused.value = it.isFocused
                if(it.isFocused && textValue.value == value){
                    textValue.value = ""
                }else if(!it.isFocused && textValue.value.isEmpty()){
                    textValue.value = value
                }
            })
}