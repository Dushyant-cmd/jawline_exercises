package com.bytezaptech.jawlineexercise_faceyoga.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.bytezaptech.jawlineexercise_faceyoga.data.local.entities.UserExerciseDetails

@Dao
interface UserExerciseDao {
    @Insert
    fun insert(userExercise: UserExerciseDetails)

    @Query("SELECT * FROM userExerciseDetails")
    fun getDetails(): UserExerciseDetails

    @Query("DELETE FROM userExerciseDetails")
    fun deleteAll(): Int

    @Update
    fun update(userExercise: UserExerciseDetails)

    @Delete
    fun delete(userExercise: UserExerciseDetails)
}