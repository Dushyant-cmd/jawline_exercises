package com.bytezaptech.jawlineexercise_faceyoga.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.bytezaptech.jawlineexercise_faceyoga.data.local.entities.ArticleEntity
import com.bytezaptech.jawlineexercise_faceyoga.data.local.entities.ExerciseChallenge

@Dao
interface ArticleDao {
    @Insert
    fun insert(article: ArticleEntity)
    @Insert
    fun insertAll(article: List<ArticleEntity>)

    @Query("SELECT * FROM articles")
    fun getAll(): List<ArticleEntity>

    @Query("DELETE FROM articles")
    fun deleteAll()

    @Update
    fun update(article: ArticleEntity)

    @Delete
    fun delete(article: ArticleEntity)
}