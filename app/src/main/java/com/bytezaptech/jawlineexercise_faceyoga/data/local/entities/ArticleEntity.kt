package com.bytezaptech.jawlineexercise_faceyoga.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "articles")
data class ArticleEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int?, val title: String?, val description: String?, val image: Int?
)