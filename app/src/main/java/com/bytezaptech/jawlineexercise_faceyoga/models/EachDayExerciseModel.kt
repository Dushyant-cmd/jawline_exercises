package com.bytezaptech.jawlineexercise_faceyoga.models

import java.io.Serializable

data class EachDayExerciseModel(val day: String?,
    val img: Int?,
    val title: String?,
    val duration: String?,
    val description: String?) : Serializable
