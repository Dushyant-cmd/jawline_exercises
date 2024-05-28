package com.bytezaptech.jawlineexercise_faceyoga.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "exercise_challenge_table")
data class ExerciseChallenge(
    @PrimaryKey(autoGenerate = true)
    val id: Int?,
    @ColumnInfo(name = "name")
    val name: String?,
    @ColumnInfo(name = "daysCompleted", defaultValue = "1")
    val daysCompleted: Int?,
    @ColumnInfo(name = "totalDays", defaultValue = "1")
    val totalDays: Int?,
    @ColumnInfo(name = "isFinished", defaultValue = "false")
    val isFinished: Boolean?): Serializable