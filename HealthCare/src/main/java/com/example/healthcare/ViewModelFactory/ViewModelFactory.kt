package com.example.healthcare.ViewModelFactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ViewModelFactory(private val creator: () -> ViewModel) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return try {
            @Suppress("UNCHECKED_CAST")
            creator() as T
        } catch (e: Exception) {
            throw RuntimeException("Error creating ViewModel", e)
        }
    }
}