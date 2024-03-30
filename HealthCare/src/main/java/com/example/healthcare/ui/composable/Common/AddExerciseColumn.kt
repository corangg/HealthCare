package com.example.healthcare.ui.composable.Common

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.MutableLiveData
import com.example.healthcare.ExerciseItem

import com.example.healthcare.R

@Composable
fun AddExerciseColumn(
    day : Int,
    list : MutableLiveData<MutableList<ExerciseItem>>,
    edit : Boolean,
    onDeleteClicked : (String, Int) -> Unit,
    onAddClicked: (String, Int)-> Unit,
    onUpdate: (String,String,Int)->Unit) {
    val dayOfTheWeek = arrayOf("일","월","화","수","목","금","토")
    val exerciseList by list.observeAsState(initial = listOf())

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .background(Color.Transparent)
            .width(86.dp)) {
        Text(
            text = dayOfTheWeek[day],
            style = TextStyle(color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold),
        )
        Spacer(modifier = Modifier.height(12.dp))


        exerciseList.forEach { exercise ->
            key(exercise.id) {
                SelectExerciseSpinner(
                    exercise = exercise.name,
                    onExerciseSelected = { newName ->
                        onUpdate(exercise.id,newName,day)
                    },
                    onDeleteClicked = {
                        onDeleteClicked(exercise.id,day)
                    },
                    list = listOf("유산소", "등", "가슴", "하체", "어깨", "팔", "허리"),
                    edit = edit,
                    select = edit
                )
                Spacer(modifier = Modifier.height(4.dp))
            }
        }
        if(edit){
            Image(
                painter = painterResource(id = R.drawable.ic_add),
                contentDescription = "AddExercise",
                colorFilter = ColorFilter.tint(Color.White),
                modifier = Modifier
                    .padding(8.dp)
                    .size(30.dp)
                    .clickable {
                        onAddClicked("선택", day)
                    }
            )
        }
    }
}
