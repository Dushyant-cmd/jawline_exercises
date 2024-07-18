package com.bytezaptech.jawlineexercise_faceyoga.data.local.entities

import android.graphics.Bitmap
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("growthTable")
data class GrowthEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int?,
    val challengeName: String?,
    val day: Int?,
    val formattedDate: String?,
    val timestamp: Long?,
    val growthImg: String?,
    val totalDays: Int?)