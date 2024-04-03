package com.bytezaptech.jawlineexercise_faceyoga.ui.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bytezaptech.jawlineexercise_faceyoga.data.repositories.AuthRepository

class LoginAndSignUpViewModel(val authRepo: AuthRepository): ViewModel() {
}

class LoginAndSignUpViewModelFactory(val authRepo: AuthRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return LoginAndSignUpViewModel(authRepo) as T
    }
}