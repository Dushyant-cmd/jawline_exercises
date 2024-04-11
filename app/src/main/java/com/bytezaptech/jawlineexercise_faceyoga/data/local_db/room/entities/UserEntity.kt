package com.bytezaptech.jawlineexercise_faceyoga.data.local_db.room.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class UserEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int?,
    val name: String?,
    val email: String?,
    val plan: String?,
    val planType: Int?) {
}