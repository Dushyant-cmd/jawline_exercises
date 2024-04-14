package com.bytezaptech.jawlineexercise_faceyoga.data.local_db.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.bytezaptech.jawlineexercise_faceyoga.data.local_db.room.dao.UserDao
import com.bytezaptech.jawlineexercise_faceyoga.data.local_db.room.entities.UserEntity

@Database(entities = [UserEntity::class], exportSchema = false, version = 1)
abstract class RoomDb: RoomDatabase() {

    abstract fun getUserDao(): UserDao

    companion object {
        private val DB_NAME = "room_db"
        fun getInstance(context: Context): RoomDb {
            return Room.databaseBuilder(context, RoomDb::class.java, DB_NAME).allowMainThreadQueries().fallbackToDestructiveMigration().build()
        }
    }
}