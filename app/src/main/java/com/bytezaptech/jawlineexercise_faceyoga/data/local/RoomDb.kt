package com.bytezaptech.jawlineexercise_faceyoga.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.bytezaptech.jawlineexercise_faceyoga.data.local.dao.ArticleDao
import com.bytezaptech.jawlineexercise_faceyoga.data.local.dao.ExerciseChallengeDao
import com.bytezaptech.jawlineexercise_faceyoga.data.local.dao.GrowthDao
import com.bytezaptech.jawlineexercise_faceyoga.data.local.dao.LanguageDao
import com.bytezaptech.jawlineexercise_faceyoga.data.local.dao.OneTwentyExerciseDao
import com.bytezaptech.jawlineexercise_faceyoga.data.local.dao.SixtyDaysExerciseDao
import com.bytezaptech.jawlineexercise_faceyoga.data.local.dao.ThirtyDaysExerciseDao
import com.bytezaptech.jawlineexercise_faceyoga.data.local.dao.UserDao
import com.bytezaptech.jawlineexercise_faceyoga.data.local.dao.UserExerciseDao
import com.bytezaptech.jawlineexercise_faceyoga.data.local.entities.ArticleEntity
import com.bytezaptech.jawlineexercise_faceyoga.data.local.entities.ExerciseChallenge
import com.bytezaptech.jawlineexercise_faceyoga.data.local.entities.GrowthEntity
import com.bytezaptech.jawlineexercise_faceyoga.data.local.entities.LanguageEntity
import com.bytezaptech.jawlineexercise_faceyoga.data.local.entities.OneTwentyDaysExerciseEntity
import com.bytezaptech.jawlineexercise_faceyoga.data.local.entities.SixtyDaysExerciseEntity
import com.bytezaptech.jawlineexercise_faceyoga.data.local.entities.ThirtyDaysExerciseEntity
import com.bytezaptech.jawlineexercise_faceyoga.data.local.entities.UserEntity
import com.bytezaptech.jawlineexercise_faceyoga.data.local.entities.UserExerciseDetails

@TypeConverters(value = [com.bytezaptech.jawlineexercise_faceyoga.data.local.type_converters.TypeConverters::class])
@Database(entities = [UserEntity::class, UserExerciseDetails::class, ExerciseChallenge::class, ThirtyDaysExerciseEntity::class,
    SixtyDaysExerciseEntity::class, OneTwentyDaysExerciseEntity::class, GrowthEntity::class, ArticleEntity::class, LanguageEntity::class],
    exportSchema = false, version = 1)
abstract class RoomDb: RoomDatabase() {

    abstract fun getUserDao(): UserDao

    abstract fun getExerciseDetailsDao(): UserExerciseDao

    abstract fun getExerciseChallengeDao(): ExerciseChallengeDao

    abstract fun getThirtyDaysDao(): ThirtyDaysExerciseDao

    abstract fun getSixtyDaysDao(): SixtyDaysExerciseDao

    abstract fun getOneTwentyDaysDao(): OneTwentyExerciseDao

    abstract fun getGrowthDao(): GrowthDao

    abstract fun getArticleDao(): ArticleDao

    abstract fun getLanguageDao(): LanguageDao

    companion object {
        private val DB_NAME = "room_db"
        fun getInstance(context: Context): RoomDb {
            return Room.databaseBuilder(context, RoomDb::class.java, DB_NAME).allowMainThreadQueries().fallbackToDestructiveMigration().build()
        }
    }
}