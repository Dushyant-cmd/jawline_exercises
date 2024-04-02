package com.bytezaptech.jawlineexercise_faceyoga.di

import android.content.Context
import com.bytezaptech.jawlineexercise_faceyoga.ui.main.MainActivity
import com.bytezaptech.jawlineexercise_faceyoga.ui.splash.SplashActivity
import dagger.Component

@Component
interface AppComponent {

    @Component.Factory
    interface Factory {
        fun create(context: Context): AppComponent
    }

    fun inject(mainActivity: MainActivity)
    fun inject(splashActivity: SplashActivity)
}