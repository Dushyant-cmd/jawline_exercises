package com.bytezaptech.jawlineexercise_faceyoga.ui.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.bytezaptech.jawlineexercise_faceyoga.data.local.entities.UserExerciseDetails
import com.bytezaptech.jawlineexercise_faceyoga.data.repositories.AuthRepository
import com.bytezaptech.jawlineexercise_faceyoga.utils.Error
import com.bytezaptech.jawlineexercise_faceyoga.utils.Response
import com.bytezaptech.jawlineexercise_faceyoga.utils.Success
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class OnBoardDetailsViewModel(private val authRepository: AuthRepository) : ViewModel() {
    private val genderMutLiveData: MutableLiveData<Response> = MutableLiveData()
    val genderLiveData: LiveData<Response>
        get() {
            return genderMutLiveData
        }

    fun updateUserDetails(userExerciseDetails: UserExerciseDetails) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                authRepository.updateUserDetails(userExerciseDetails)
            }
        }
    }

    fun submitGender(gender: String) {
        if (gender.isEmpty())
            genderMutLiveData.value = Error("Please select gender")
        else
            genderMutLiveData.value = Success(gender)
    }
}

class OnBoardDetailsViewModelFactory(val authRepository: AuthRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return OnBoardDetailsViewModel(authRepository) as T
    }
}
