package com.bytezaptech.jawlineexercise_faceyoga.models

import com.bytezaptech.jawlineexercise_faceyoga.data.local.entities.ExerciseChallenge
import java.io.Serializable

data class ExerciseListModel(val name: String, val day: Int,  val isFinished: Boolean, val exerciseChallenge: ExerciseChallenge): Serializable
