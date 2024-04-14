package com.bytezaptech.jawlineexercise_faceyoga.ui.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.bytezaptech.jawlineexercise_faceyoga.data.repositories.AuthRepository
import com.bytezaptech.jawlineexercise_faceyoga.utils.Response
import com.google.firebase.auth.AuthCredential
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginAndSignUpViewModel(private val authRepo: AuthRepository): ViewModel() {
    val authLiveData: LiveData<Response>
        get() {
            return authRepo.authLiveData
        }

    fun signInOrSignUp(authCredential: AuthCredential, name: String, email: String) {
        viewModelScope.launch(Dispatchers.Main) {
            authRepo.signOrSignUpUser(authCredential, name, email)
        }
    }
}

class LoginAndSignUpViewModelFactory(val authRepo: AuthRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return LoginAndSignUpViewModel(authRepo) as T
    }
}