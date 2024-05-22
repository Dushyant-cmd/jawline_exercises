package com.bytezaptech.jawlineexercise_faceyoga.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.bytezaptech.jawlineexercise_faceyoga.data.local.entities.ExerciseChallenge

@Dao
interface ExerciseChallengeDao {

    @Insert
    fun insert(exerciseChallenge: ExerciseChallenge)

    @Query("SELECT * FROM exercise_challenge_table")
    fun getAll(): List<ExerciseChallenge>

    @Query("DELETE FROM exercise_challenge_table")
    fun deleteAll()

    @Update
    fun update(exerciseChallenge: ExerciseChallenge)

    @Delete
    fun delete(exerciseChallenge: ExerciseChallenge)
}