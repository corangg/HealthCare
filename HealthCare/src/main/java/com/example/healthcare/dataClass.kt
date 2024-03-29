package com.example.healthcare

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "phsical_info")
data class PhsicalInfo(
    @PrimaryKey
    val name: String = "",
    val gender: Boolean = true,
    val age : Int = 0,
    val height : Float = 0f,
    val weight : Float = 0f
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
    val timeStamp : Long = 0,
    val weight: Float = 0f
)

@Entity(tableName = "exercisedata_records")
data class ExerciseDataRecord(
    @PrimaryKey
    val timeStamp : Long,
    val exerciseType: List<ExerciseType>,
)
data class ExerciseType(
    val exerciseType : String,
    val exerciseInfo : List<ExerciseInfo>
)

data class ExerciseInfo(
    val exercise : String,
    val weight : Float = 0f,
    val set : Float = 0f,
    val number : Float = 0f
)

data class ExerciseTypeList(
    val exerciseType : String,
    val exerciseList : MutableList<String>
)

data class ExerciseRecord(
    val exerciseName : String,
    val exerciseList : List<ExerciseTimeRecord>
)

data class ExerciseTimeRecord(
    val timeStamp : Long,
    val info : ExerciseInfo
)

data class GraphColor(
    val lineColor : Color = Color.White,
    val pointColor : Color = Color.White,
    val indicatorColor : Color = Color.White,
    val indicatorLineColor : Color = Color.White
)

data class UnitList(
    val info : List<String>,
    val unit : List<String>
)


