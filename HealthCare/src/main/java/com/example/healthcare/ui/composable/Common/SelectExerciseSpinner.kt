package com.example.healthcare.ui.composable.Common

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.healthcare.R

@Composable
fun SelectExerciseSpinner(
    exercise: String,
    onExerciseSelected: (String) -> Unit,
    onDeleteClicked: () -> Unit,
    modifier: Modifier = Modifier
        .width(84.dp)
        .padding(horizontal = 4.dp),
    textModifier: Modifier = Modifier
        .padding(vertical = 10.dp)
        .width(56.dp)
        .wrapContentSize(Alignment.Center),
    dropDownModifier: Modifier = Modifier
        .width(76.dp)
        .heightIn(max = 240.dp)
        .background(Color(0xFF2D2D2D), RoundedCornerShape(8.dp))
        .padding(vertical = 1.dp),
    list: List<String>,
    edit: Boolean,
    select : Boolean){
    var expanded by remember { mutableStateOf(false) }
    val options = list
    var selectedOption by remember { mutableStateOf(exercise) }

    Box(modifier = modifier,
        contentAlignment = Alignment.Center,
    ) {

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .background(Color(0xFF2D2D2D), RoundedCornerShape(8.dp))
                .border(1.dp, Color.White, RoundedCornerShape(8.dp))) {
            Text(
                text = selectedOption,
                style = TextStyle(color = Color.White, fontSize = 16.sp),
                modifier = textModifier
                    .clickable { expanded = true }/*Modifier
                    .clickable { expanded = true }
                    .padding(vertical = 10.dp)
                    .width(56.dp)
                    .wrapContentSize(Alignment.Center)*/,
            )
            if(edit){
                Image(
                    painter = painterResource(id = R.drawable.ic_delete),
                    contentDescription = "DelExercise",
                    colorFilter = ColorFilter.tint(Color.White),
                    modifier = Modifier
                        .size(22.dp)
                        .padding(end = 4.dp)
                        .clickable(onClick = onDeleteClicked)
                )
            }
        }
        if(select){
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = dropDownModifier/*Modifier
                    .width(76.dp)
                    .heightIn(max = 240.dp)
                    .background(Color(0xFF2D2D2D), RoundedCornerShape(8.dp))
                    .padding(vertical = 1.dp)*/
            ) {
                options.forEach { option ->
                    DropdownMenuItem(
                        text = { Text(option) },
                        onClick = {
                            selectedOption = option
                            onExerciseSelected(option)
                            expanded = false
                        }
                    )
                }
            }
        }
    }
}
