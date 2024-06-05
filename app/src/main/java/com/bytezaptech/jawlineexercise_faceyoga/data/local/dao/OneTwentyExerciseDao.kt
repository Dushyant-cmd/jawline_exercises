package com.bytezaptech.jawlineexercise_faceyoga.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.bytezaptech.jawlineexercise_faceyoga.data.local.entities.OneTwentyDaysExerciseEntity
import com.bytezaptech.jawlineexercise_faceyoga.data.local.entities.ThirtyDaysExerciseEntity


@Dao
interface OneTwentyExerciseDao {
    @Insert
    fun insert(daysEntity: OneTwentyDaysExerciseEntity)
    @Insert
    fun insertAll(daysEntity: List<OneTwentyDaysExerciseEntity>)

    @Query("SELECT * FROM oneTwentyDaysExercise")
    fun getDetails(): List<OneTwentyDaysExerciseEntity>

    @Query("DELETE FROM oneTwentyDaysExercise")
    fun deleteAll(): Int

    @Update
    fun update(daysEntity: OneTwentyDaysExerciseEntity)

    @Delete
    fun delete(daysEntity: OneTwentyDaysExerciseEntity)

    @Query("SELECT * FROM thirtyDays WHERE day = :day")
    fun getExerciseByDay(day: String): ThirtyDaysExerciseEntity
}