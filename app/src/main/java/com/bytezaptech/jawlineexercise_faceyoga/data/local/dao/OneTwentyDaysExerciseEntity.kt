package com.bytezaptech.jawlineexercise_faceyoga.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.bytezaptech.jawlineexercise_faceyoga.data.local.entities.OneTwentyDaysExerciseEntity


@Dao
interface OneTwentyDaysExerciseEntity {
    @Insert
    fun insert(daysEntity: OneTwentyDaysExerciseEntity)

    @Query("SELECT * FROM oneTwentyDaysExercise")
    fun getDetails(): OneTwentyDaysExerciseEntity

    @Query("DELETE FROM oneTwentyDaysExercise")
    fun deleteAll(): Int

    @Update
    fun update(daysEntity: OneTwentyDaysExerciseEntity)

    @Delete
    fun delete(daysEntity: OneTwentyDaysExerciseEntity)
}