package com.bytezaptech.jawlineexercise_faceyoga.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.bytezaptech.jawlineexercise_faceyoga.data.local.entities.SixtyDaysExerciseEntity


@Dao
interface SixtyDaysExerciseDao {
    @Insert
    fun insert(daysEntity: SixtyDaysExerciseEntity)

    @Query("SELECT * FROM sixtyDays")
    fun getExercises(): SixtyDaysExerciseEntity

    @Query("DELETE FROM sixtyDays")
    fun deleteAll(): Int

    @Update
    fun update(daysEntity: SixtyDaysExerciseEntity)

    @Delete
    fun delete(daysEntity: SixtyDaysExerciseEntity)
}