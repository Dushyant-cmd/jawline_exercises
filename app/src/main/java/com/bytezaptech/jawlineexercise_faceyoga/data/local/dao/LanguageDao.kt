package com.bytezaptech.jawlineexercise_faceyoga.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.bytezaptech.jawlineexercise_faceyoga.data.local.entities.LanguageEntity

@Dao
interface LanguageDao {

    @Insert
    fun insert(data: LanguageEntity)
    @Insert
    fun insertAll(data: List<LanguageEntity>)

    @Query("SELECT * FROM languageTable")
    fun getAll(): List<LanguageEntity>

    @Query("DELETE FROM languageTable")
    fun deleteAll()

    @Delete
    fun delete(data: LanguageEntity)
}