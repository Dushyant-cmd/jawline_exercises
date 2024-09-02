package com.bytezaptech.jawlineexercise_faceyoga.ui.settings

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.bytezaptech.jawlineexercise_faceyoga.data.local.entities.LanguageEntity
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
    val languageLD: LiveData<Response>
        get() {
            return mainRepository.languageLD
        }

    val languageSelLD: LiveData<Response>
        get() {
            return mainRepository.languageSelLD
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

    fun getLanguages() {
        viewModelScope.launch {
            mainRepository.getLanguages()
        }
    }

    fun setLanguage(languageEntity: LanguageEntity) {
        viewModelScope.launch {
            mainRepository.setLanguage(languageEntity)
        }
    }
}

class SettingsViewModelFactory(val mainRepo: MainRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return SettingsViewModel(mainRepo) as T
    }
}