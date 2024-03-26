package com.example.healthcare.ui.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun exerciseRadioButtonRow(onSelect: (String, Int) -> Unit, exerciseType : List<String>, type : Int){
    var selectedOption = remember { mutableStateOf(exerciseType[0]) }
    Row() {
        exerciseType.forEach { option ->
            exerciseRadioButton(
                label = option,
                isSelected = option == selectedOption.value,
            ){
                selectedOption.value = option
                onSelect(option, type)
            }
        }
    }
}

@Composable
fun exerciseRadioButton(label: String, isSelected: Boolean, onSelect: () -> Unit){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .width(100.dp)) {
        RadioButton(
            modifier = Modifier
                .padding(end = 1.dp),
            colors = RadioButtonDefaults.colors(
                selectedColor = Color.White
            ),
            selected = isSelected,
            onClick = onSelect
        )
        Text(text = label)
    }
}