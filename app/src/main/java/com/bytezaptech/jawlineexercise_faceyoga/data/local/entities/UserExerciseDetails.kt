package com.bytezaptech.jawlineexercise_faceyoga.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "userExerciseDetails")
data class UserExerciseDetails(
    @PrimaryKey(autoGenerate = true)
    val id:Int?,
    val gender: String?,
    val age: String?,
    val faceStructure: String?,
    val exerciseTime: String?,
    val isRegular: Boolean?,
    val reasonOfExercise: String?
)