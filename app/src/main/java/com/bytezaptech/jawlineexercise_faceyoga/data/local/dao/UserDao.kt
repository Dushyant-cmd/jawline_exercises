package com.bytezaptech.jawlineexercise_faceyoga.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.bytezaptech.jawlineexercise_faceyoga.data.local.entities.UserEntity

@Dao
interface UserDao {
    @Insert
    fun insert(userEntity: UserEntity): Long

    @Query("SELECT * FROM user LIMIT 1")
    fun getUser(): UserEntity

    @Query("DELETE FROM user")
    fun deleteUser(): Int

    @Update
    fun update(userEntity: UserEntity): Int
}