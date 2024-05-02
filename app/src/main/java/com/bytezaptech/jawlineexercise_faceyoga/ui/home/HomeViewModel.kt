package com.bytezaptech.jawlineexercise_faceyoga.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bytezaptech.jawlineexercise_faceyoga.data.repositories.MainRepository

class HomeViewModel(private val mainRepo: MainRepository): ViewModel() {
}

class HomeViewModelFactory(private val mainRepo: MainRepository): ViewModelProvider.Factory {
    override fun <T: ViewModel> create(model: Class<T>): T {
        return HomeViewModel(mainRepo) as T
    }
}