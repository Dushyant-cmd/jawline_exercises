package com.bytezaptech.jawlineexercise_faceyoga.di

import android.content.Context
import com.bytezaptech.jawlineexercise_faceyoga.ui.home.HomeFragment
import com.bytezaptech.jawlineexercise_faceyoga.ui.main.MainActivity
import com.bytezaptech.jawlineexercise_faceyoga.ui.splash.SplashActivity
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, SubComponents::class])
interface AppComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }

    fun inject(mainActivity: MainActivity)
    fun inject(splashActivity: SplashActivity)
    fun inject(homeFragment: HomeFragment)

    /**Expose subcomponent */
    fun getAuthSubcomponent(): AuthSubComponent.Factory
}