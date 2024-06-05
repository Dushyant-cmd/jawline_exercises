package com.bytezaptech.jawlineexercise_faceyoga.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.bytezaptech.jawlineexercise_faceyoga.data.local.entities.ThirtyDaysExerciseEntity

@Dao
interface ThirtyDaysExerciseDao {
    @Insert
    fun insert(daysEntity: ThirtyDaysExerciseEntity)

    @Query("SELECT * FROM thirtyDays")
    fun getExercises(): List<ThirtyDaysExerciseEntity>

    @Query("DELETE FROM thirtyDays")
    fun deleteAll(): Int

    @Update
    fun update(daysEntity: ThirtyDaysExerciseEntity)

    @Delete
    fun delete(daysEntity: ThirtyDaysExerciseEntity)
}