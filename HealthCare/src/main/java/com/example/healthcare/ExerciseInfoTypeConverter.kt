package com.example.healthcare

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class ExerciseInfoTypeConverter {
    @TypeConverter
    fun fromExerciseInfoList(exerciseInfoList: List<ExerciseInfo>?): String {
        if (exerciseInfoList == null) return ""
        return Gson().toJson(exerciseInfoList)
    }

    @TypeConverter
    fun toExerciseInfoList(exerciseInfoString: String): List<ExerciseInfo>? {
        if (exerciseInfoString.isBlank()) return emptyList()
        val type = object : TypeToken<List<ExerciseInfo>>() {}.type
        return Gson().fromJson(exerciseInfoString, type)
    }

    @TypeConverter
    fun fromExerciseTypeList(exerciseTypeList: List<ExerciseType>?): String {
        if (exerciseTypeList == null) return ""
        return Gson().toJson(exerciseTypeList)
    }

    @TypeConverter
    fun toExerciseTypeList(exerciseTypeString: String): List<ExerciseType>? {
        if (exerciseTypeString.isBlank()) return emptyList()
        val type = object : TypeToken<List<ExerciseType>>() {}.type
        return Gson().fromJson(exerciseTypeString, type)
    }

    @TypeConverter
    fun fromExerciseType(exerciseType: ExerciseType): String {
        return Gson().toJson(exerciseType)
    }

    @TypeConverter
    fun toExerciseType(exerciseTypeString: String): ExerciseType {
        val objectType = object : TypeToken<ExerciseType>() {}.type
        return Gson().fromJson(exerciseTypeString, objectType)
    }

    @TypeConverter
    fun fromStringList(value: List<String>?): String {
        if (value == null) return ""
        val type = object : TypeToken<List<String>>() {}.type
        return Gson().toJson(value, type)
    }

    @TypeConverter
    fun toStringList(value: String): List<String>? {
        if (value == "") return null
        val type = object : TypeToken<List<String>>() {}.type
        return Gson().fromJson(value, type)
    }
}