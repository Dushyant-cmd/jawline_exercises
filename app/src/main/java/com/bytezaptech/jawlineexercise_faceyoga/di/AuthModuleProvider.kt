package com.bytezaptech.jawlineexercise_faceyoga.di

import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import dagger.Module
import dagger.Provides

@Module
class AuthModuleProvider {

    @ActivityScope
    @Provides
    fun getGoogleSignOption(): GoogleSignInOptions {
        return GoogleSignInOptions.Builder()
            .requestIdToken("194895134646-0rtoipjeeco98qo6asjs8qdoj3cm7o8s.apps.googleusercontent.com")
            .requestEmail()
            .build()
    }
}