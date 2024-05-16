package com.bytezaptech.jawlineexercise_faceyoga.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.bytezaptech.jawlineexercise_faceyoga.data.repositories.MainRepository
import com.bytezaptech.jawlineexercise_faceyoga.utils.Response
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel(private val mainRepo: MainRepository): ViewModel() {
    val userProfileData: Response
        get() {
            return mainRepo.userProfile
        }

    fun getUserProfile() {
        viewModelScope.launch(Dispatchers.Main) {
            mainRepo.getUserDetails()
        }
    }
}

class HomeViewModelFactory(private val mainRepo: MainRepository): ViewModelProvider.Factory {
    override fun <T: ViewModel> create(model: Class<T>): T {
        return HomeViewModel(mainRepo) as T
    }
}