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
    val growthListAllLD: LiveData<Response>
        get() {
            return mainRepo.growthListAllLD
        }

    fun getGrowthListWithImage() {
        viewModelScope.launch {
            mainRepo.getGrowthListWithImage()
        }
    }

    fun getGrowthListWithoutImage() {
        viewModelScope.launch {
            mainRepo.getGrowthListWithoutImage()
        }
    }
}

class GrowthViewModelFactory(val mainRepo: MainRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return GrowthViewModel(mainRepo = mainRepo) as T
    }

}