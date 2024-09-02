package com.bytezaptech.jawlineexercise_faceyoga.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "languageTable")
data class LanguageEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = 0, val name:String, val langCode: String, var isSelected: Boolean)