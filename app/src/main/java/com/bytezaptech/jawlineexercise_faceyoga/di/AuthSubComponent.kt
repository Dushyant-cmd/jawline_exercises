package com.bytezaptech.jawlineexercise_faceyoga.di

import com.bytezaptech.jawlineexercise_faceyoga.ui.auth.LoginAndSIgnUp
import com.bytezaptech.jawlineexercise_faceyoga.ui.auth.LoginAndSignUpViewModel
import dagger.Subcomponent

@ActivityScope
@Subcomponent
interface AuthSubComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): AuthSubComponent
    }

    fun inject(loginAndSIgnUp: LoginAndSIgnUp)
}