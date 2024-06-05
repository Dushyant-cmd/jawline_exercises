package com.bytezaptech.jawlineexercise_faceyoga.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.bytezaptech.jawlineexercise_faceyoga.models.EachDayExerciseModel

@Entity(tableName = "sixtyDays")
data class SixtyDaysExerciseEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val day: String,
    val exercises: List<EachDayExerciseModel>)