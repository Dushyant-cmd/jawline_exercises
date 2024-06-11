package com.bytezaptech.jawlineexercise_faceyoga.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.bytezaptech.jawlineexercise_faceyoga.models.EachDayExerciseModel


@Entity(tableName = "oneTwentyDaysExercise")
data class OneTwentyDaysExerciseEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val day: String,
    val completedExercise: Int,
    val exercises: List<EachDayExerciseModel>)
