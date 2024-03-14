package com.example.healthcare

import androidx.compose.ui.graphics.vector.ImageVector
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "phsical_info")
data class PhsicalInfo(
    @PrimaryKey
    val name: String,
    val gender: Boolean,
    val age : Int,
    val height : Float,
    val weight : Float
)

@Entity(tableName = "exercise_items")
data class ExerciseItem(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    val name: String,
    val dayOfWeek: Int
)

@Entity(tableName = "weight_data")
data class WeightData(
    @PrimaryKey
    val timeStamp : Long,
    val weight: Float
)

/*
@Entity(tableName = "chest_exercise")
data class ChestExercise(
    @PrimaryKey
    val timeStamp : Long,
    val exerciseInfo : List<ExerciseInfo>
)
*/

@Entity(tableName = "exercise_records")
data class ExerciseRecord(
    @PrimaryKey
    /*val timeStamp : Long,//이게 겹쳐서 그렇잖아? 2개를 하나의 데이터클래스로 묶으면?
    val exerciseType : String,*/
    val exerciseType: ExerciseType,
    val exerciseInfo : List<ExerciseInfo>
    /*val exercise : String,
    val weight : Int = 0,
    val set : Int = 0,
    val number : Int = 0*/
)

data class ExerciseType(
    val timeStamp : Long,
    val exerciseType : String,
)

data class ExerciseInfo(
    val exercise : String,
    val weight : Int = 0,
    val set : Int = 0,
    val number : Int = 0
)

data class NavigationItem(val title: String, val icon: ImageVector)
