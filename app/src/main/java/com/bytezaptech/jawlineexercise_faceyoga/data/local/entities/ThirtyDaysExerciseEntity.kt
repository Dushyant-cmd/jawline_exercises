package com.bytezaptech.jawlineexercise_faceyoga.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "thirtyDays")
data class ThirtyDaysExerciseEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val day: String,
    val exercises: String)