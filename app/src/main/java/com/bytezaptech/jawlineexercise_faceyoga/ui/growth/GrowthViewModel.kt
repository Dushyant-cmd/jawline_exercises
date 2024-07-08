package com.bytezaptech.jawlineexercise_faceyoga.ui.growth

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.bytezaptech.jawlineexercise_faceyoga.data.repositories.MainRepository
import com.bytezaptech.jawlineexercise_faceyoga.utils.Response
import kotlinx.coroutines.launch

class GrowthViewModel(val mainRepo: MainRepository) : ViewModel() {
    val growthListLD: LiveData<Response>
        get() {
            return mainRepo.growthListLD
        }

    fun getGrowthList() {
        viewModelScope.launch {
            mainRepo.getGrowthList()
        }
    }
}

class GrowthViewModelFactory(val mainRepo: MainRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return GrowthViewModel(mainRepo = mainRepo) as T
    }

}