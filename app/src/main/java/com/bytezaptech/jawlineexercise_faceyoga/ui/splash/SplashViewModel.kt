package com.bytezaptech.jawlineexercise_faceyoga.ui.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.bytezaptech.jawlineexercise_faceyoga.data.repositories.MainRepository
import com.bytezaptech.jawlineexercise_faceyoga.utils.Response
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashViewModel(val repo: MainRepository) : ViewModel() {
    val authAuthLiveData: LiveData<Response>
        get() {
            return repo.splashAuthLiveData
        }

    fun isUserLoggedIn() {
        viewModelScope.launch {
            repo.isUserLoggedIn()
            delay(2000)
        }
    }
}

class SplashViewModelFactory(val repo: MainRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return SplashViewModel(repo) as T
    }
}