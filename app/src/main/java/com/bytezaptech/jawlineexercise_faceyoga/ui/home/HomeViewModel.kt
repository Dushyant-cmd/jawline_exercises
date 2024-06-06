package com.bytezaptech.jawlineexercise_faceyoga.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.bytezaptech.jawlineexercise_faceyoga.data.repositories.MainRepository
import com.bytezaptech.jawlineexercise_faceyoga.models.ExerciseListModel
import com.bytezaptech.jawlineexercise_faceyoga.utils.ExerciseResponse
import com.bytezaptech.jawlineexercise_faceyoga.utils.Response
import com.bytezaptech.jawlineexercise_faceyoga.utils.Success
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel(private val mainRepo: MainRepository): ViewModel() {
    val userProfileData: LiveData<Response>
        get() {
            return mainRepo.userProfile
        }

    val exerciseDetails: MutableLiveData<Response> = MutableLiveData()

    val exerciseChallenge: LiveData<Response>
        get() {
            return mainRepo.exerciseChallenge
        }

    val exerciseDayLiveData: LiveData<ExerciseResponse>
        get() {
            return mainRepo.exerciseDayLiveData
        }

    fun getUserProfile() {
        viewModelScope.launch(Dispatchers.Main) {
            mainRepo.getUserDetails()
        }
    }

    fun openExerciseDetails(exerciseModel: ExerciseListModel) {
        exerciseDetails.value = Success(exerciseModel)
    }

    fun addExerciseChallenges() {
        viewModelScope.launch(Dispatchers.Main) {
            mainRepo.addExerciseChallenges()
        }
    }

    fun getThirtyDayExercise(day: String) {
        viewModelScope.launch(Dispatchers.Main) {
            mainRepo.getThirtyDayExercise(day)
        }
    }

    fun getSixtyDayExercise(day: String) {
        viewModelScope.launch(Dispatchers.Main) {
            mainRepo.getSixtyDayExercise(day)
        }
    }

    fun getOneTwentyDayExercise(day: String) {
        viewModelScope.launch(Dispatchers.Main) {
            mainRepo.getOneTwentyDayExercise(day)
        }
    }
}

class HomeViewModelFactory(private val mainRepo: MainRepository): ViewModelProvider.Factory {
    override fun <T: ViewModel> create(model: Class<T>): T {
        return HomeViewModel(mainRepo) as T
    }
}