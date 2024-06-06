package com.bytezaptech.jawlineexercise_faceyoga.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.bytezaptech.jawlineexercise_faceyoga.data.local.entities.SixtyDaysExerciseEntity
import com.bytezaptech.jawlineexercise_faceyoga.data.local.entities.ThirtyDaysExerciseEntity


@Dao
interface SixtyDaysExerciseDao {
    @Insert
    fun insert(daysEntity: SixtyDaysExerciseEntity)

    @Insert
    fun insertAll(daysEntity: List<SixtyDaysExerciseEntity>)

    @Query("SELECT * FROM sixtyDays")
    fun getExercises(): List<SixtyDaysExerciseEntity>

    @Query("DELETE FROM sixtyDays")
    fun deleteAll(): Int

    @Update
    fun update(daysEntity: SixtyDaysExerciseEntity)

    @Delete
    fun delete(daysEntity: SixtyDaysExerciseEntity)

    @Query("SELECT * FROM sixtyDays WHERE day = :day")
    fun getExerciseByDay(day: String): SixtyDaysExerciseEntity
}