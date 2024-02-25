package com.example.healthcare

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "exercise_items")
data class ExerciseItem(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    val name: String,
    val dayOfWeek: Int
)
