package com.bytezaptech.jawlineexercise_faceyoga.di

import android.content.Context
import android.content.SharedPreferences
import com.bytezaptech.jawlineexercise_faceyoga.data.local.RoomDb
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {

    @Singleton
    @Provides
    fun getSharedPref(context: Context): SharedPreferences {
        return context.getSharedPreferences(context.packageName, Context.MODE_PRIVATE)
    }

    @Singleton
    @Provides
    fun getFirestore(): FirebaseFirestore {
        return FirebaseFirestore.getInstance()
    }

    @Singleton
    @Provides
    fun getFirebaseAuth(): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }

    @Singleton
    @Provides
    fun getRoomInstance(context: Context): RoomDb {
        return RoomDb.getInstance(context)
    }
}