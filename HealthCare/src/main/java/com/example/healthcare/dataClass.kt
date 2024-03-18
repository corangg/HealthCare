package com.example.healthcare

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
    val weight : Int = 0,
    val set : Int = 0,
    val number : Int = 0
)

