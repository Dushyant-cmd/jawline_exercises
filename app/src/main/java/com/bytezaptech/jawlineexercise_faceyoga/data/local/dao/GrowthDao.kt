package com.bytezaptech.jawlineexercise_faceyoga.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.bytezaptech.jawlineexercise_faceyoga.data.local.entities.GrowthEntity

@Dao
interface GrowthDao {
    @Query("SELECT * FROM growthTable")
    fun getGrowthList(): List<GrowthEntity>

    @Insert
    suspend fun insert(growthEntity: GrowthEntity)

    @Query("DELETE FROM growthTable")
    suspend fun deleteAll()

    @Query("SELECT * FROM growthTable WHERE day = :day AND formattedDate = :formattedDate")
    suspend fun getGrowthById(day: Int, formattedDate: String): GrowthEntity

    @Update
    suspend fun update(growthEntity: GrowthEntity)

    @Delete
    suspend fun delete(growthEntity: GrowthEntity)
}