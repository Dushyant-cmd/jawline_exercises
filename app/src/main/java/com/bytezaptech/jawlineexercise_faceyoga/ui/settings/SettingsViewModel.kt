package com.bytezaptech.jawlineexercise_faceyoga.ui.settings

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.bytezaptech.jawlineexercise_faceyoga.data.repositories.MainRepository
import com.bytezaptech.jawlineexercise_faceyoga.utils.Response
import kotlinx.coroutines.launch

class SettingsViewModel(private val mainRepository: MainRepository) : ViewModel() {
    val profileLD: LiveData<Response>
        get() {
            return mainRepository.userProfile
        }

    val signOutLD: LiveData<Response>
        get() {
            return mainRepository.signOutLD
        }

    fun getProfile() {
        viewModelScope.launch {
            mainRepository.getUserDetails()
        }
    }

    fun signOutUser() {
        viewModelScope.launch {
            mainRepository.signOutUser()
        }
    }

    fun deleteAccount() {
        viewModelScope.launch {
            mainRepository.deleteAccount()
        }
    }
}

class SettingsViewModelFactory(val mainRepo: MainRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return SettingsViewModel(mainRepo) as T
    }
}