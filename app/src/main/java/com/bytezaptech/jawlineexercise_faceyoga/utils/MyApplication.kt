package com.bytezaptech.jawlineexercise_faceyoga.utils

import android.app.Application
import com.bytezaptech.jawlineexercise_faceyoga.di.AppComponent

class MyApplication: Application() {
    lateinit var appComponent: AppComponent
    override fun onCreate() {
        super.onCreate()
        initialize()
    }

    private fun initialize() {
//        appComponent = Dagge
    }
}