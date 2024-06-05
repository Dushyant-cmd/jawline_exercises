package com.bytezaptech.jawlineexercise_faceyoga.di

import android.content.Context
import com.bytezaptech.jawlineexercise_faceyoga.ui.exercise_details.ExerciseDetailsFragment
import com.bytezaptech.jawlineexercise_faceyoga.ui.home.HomeFragment
import com.bytezaptech.jawlineexercise_faceyoga.ui.home.OneTwentyDaysFragment
import com.bytezaptech.jawlineexercise_faceyoga.ui.home.SixtyDaysFragment
import com.bytezaptech.jawlineexercise_faceyoga.ui.home.ThirtyDaysFragment
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
    fun inject(thirtyDaysFragment: ThirtyDaysFragment)
    fun inject(sixtyDaysFragment: SixtyDaysFragment)
    fun inject(oneTwentyDaysFragment: OneTwentyDaysFragment)
    fun inject(exerciseDetailsFragment: ExerciseDetailsFragment)

    /**Expose subcomponent */
    fun getAuthSubcomponent(): AuthSubComponent.Factory
}