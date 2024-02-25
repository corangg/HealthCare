package com.example.healthcare

import java.util.UUID

data class SpinnerState(
    val id: String,
    val showSpinner: Boolean
    )

data class DayExerciseSpinner(
    var mon : MutableList<ExerciseItem>,
    var tues : MutableList<ExerciseItem>,
    var wednes : MutableList<ExerciseItem>,
    var thurs : MutableList<ExerciseItem>,
    var fri : MutableList<ExerciseItem>,
    var satur : MutableList<ExerciseItem>,
    var sun : MutableList<ExerciseItem>,
)

data class ExerciseItem(
    val id: UUID = UUID.randomUUID(),
    val name: String
)