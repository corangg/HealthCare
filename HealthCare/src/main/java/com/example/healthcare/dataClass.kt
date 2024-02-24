package com.example.healthcare

import java.util.UUID

data class SpinnerState(
    val id: String,
    val showSpinner: Boolean
    )

data class ExerciseItem(
    val id: UUID = UUID.randomUUID(),
    val name: String
)