package com.bytezaptech.jawlineexercise_faceyoga.ui.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bytezaptech.jawlineexercise_faceyoga.data.repositories.MainRepository

class SettingsViewModel(val mainRepository: MainRepository): ViewModel() {
}

class SettingsViewModelFactory(val mainRepo: MainRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return SettingsViewModel(mainRepo) as T
    }
}