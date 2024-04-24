package com.bytezaptech.jawlineexercise_faceyoga.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "userExerciseDetails")
data class UserExerciseDetails(
    @PrimaryKey(autoGenerate = true)
    val id:Int?,
    var name: String?,
    var gender: String?,
    var age: String?,
    var weight: String?,
    var faceStructure: String?,
    var exerciseTime: String?,
    var isRegular: Boolean?,
    var reasonOfExercise: String?
) {
    constructor(): this(0, "", "", "", "", "", "", false, "")
}