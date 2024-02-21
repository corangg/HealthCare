package com.example.healthcare.ui.composable

import android.text.Layout.Alignment
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.healthcare.VIewModel.InformationInputViewModel

@Composable
fun SliderInputRow(
    iconId: Int,
    iconDescription: String,
    sliderValue: Float,
    onSliderValueChange: (Float) -> Unit,
    sliderMaxValue: Float,
    textValue: String,
    onTextValueChange: (String) -> Unit,
    unitText: String,
    focusRequester: FocusRequester
) {
    Row(verticalAlignment = androidx.compose.ui.Alignment.CenterVertically) {
        Image(
            painter = painterResource(id = iconId),
            contentDescription = iconDescription,
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .border(border = BorderStroke(3.dp, Color.White), shape = CircleShape)
                .padding(8.dp),
            colorFilter = ColorFilter.tint(Color.White)
        )
        Slider(
            value = sliderValue,
            onValueChange = onSliderValueChange,
            valueRange = 0f..sliderMaxValue,
            colors = SliderDefaults.colors(
                activeTrackColor = Color.White,
                inactiveTrackColor = Color.Gray,
                thumbColor = Color.White
            ),
            modifier = Modifier
                .width(160.dp)
                .padding(start = 10.dp)
        )
        BasicTextField(
            value = textValue,
            onValueChange = onTextValueChange,
            textStyle = TextStyle(color = Color.White, fontSize = 16.sp),
            singleLine = true,
            cursorBrush = SolidColor(Color.White),
            modifier = Modifier
                .padding(start = 10.dp)
                .background(Color(0xFF2D2D2D), RoundedCornerShape(8.dp))
                .padding(horizontal = 10.dp, vertical = 8.dp)
                .width(50.dp)
                .focusRequester(focusRequester),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
        )
        Text(
            text = unitText,
            modifier = Modifier.padding(start = 10.dp).width(36.dp),
            color = Color.White,
            fontSize = 22.sp
        )
    }
}