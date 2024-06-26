package com.bytezaptech.jawlineexercise_faceyoga.di

import com.bytezaptech.jawlineexercise_faceyoga.ui.auth.AuthBottomSheetFragment
import com.bytezaptech.jawlineexercise_faceyoga.ui.auth.LoginAndSIgnUp
import com.bytezaptech.jawlineexercise_faceyoga.ui.onboard_details.OnboardDetailsActivity
import dagger.Subcomponent

@ActivityScope
@Subcomponent(modules = [AuthModuleProvider::class])
interface AuthSubComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): AuthSubComponent
    }

    fun inject(loginAndSIgnUp: LoginAndSIgnUp)
    fun inject(authBottomSheet: AuthBottomSheetFragment)
    fun inject(onboardDetailsActivity: OnboardDetailsActivity)
}