package com.bytezaptech.jawlineexercise_faceyoga.ui.growth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bytezaptech.jawlineexercise_faceyoga.data.repositories.MainRepository

class GrowthViewModel(val mainRepo: MainRepository): ViewModel() {
}

class GrowthViewModelFactory(val mainRepo: MainRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return GrowthViewModel(mainRepo = mainRepo) as T
    }

}