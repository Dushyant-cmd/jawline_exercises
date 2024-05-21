package com.bytezaptech.jawlineexercise_faceyoga.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.bytezaptech.jawlineexercise_faceyoga.data.local.entities.ExerciseListModel
import com.bytezaptech.jawlineexercise_faceyoga.data.repositories.MainRepository
import com.bytezaptech.jawlineexercise_faceyoga.utils.Response
import com.bytezaptech.jawlineexercise_faceyoga.utils.Success
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel(private val mainRepo: MainRepository): ViewModel() {
    val userProfileData: Response
        get() {
            return mainRepo.userProfile
        }

    val exerciseDetails: MutableLiveData<Response> = MutableLiveData()

    fun getUserProfile() {
        viewModelScope.launch(Dispatchers.Main) {
            mainRepo.getUserDetails()
        }
    }

    fun openExerciseDetails(exerciseModel: ExerciseListModel) {
        exerciseDetails.value = Success(exerciseModel)
    }
}

class HomeViewModelFactory(private val mainRepo: MainRepository): ViewModelProvider.Factory {
    override fun <T: ViewModel> create(model: Class<T>): T {
        return HomeViewModel(mainRepo) as T
    }
}