package com.bytezaptech.jawlineexercise_faceyoga.data.local_db.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.bytezaptech.jawlineexercise_faceyoga.data.local_db.room.entities.UserEntity

@Dao
interface UserDao {
    @Insert
    fun insert(userEntity: UserEntity): Long

    @Query("SELECT * FROM user")
    fun getUser(): UserEntity

    @Query("DELETE FROM user")
    fun deleteUser(): Int

    @Update
    fun update(userEntity: UserEntity): Int
}