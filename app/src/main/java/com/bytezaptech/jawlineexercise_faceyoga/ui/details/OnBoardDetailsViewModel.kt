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

    private val nameMutLiveData: MutableLiveData<Response> = MutableLiveData()

    val nameLiveData: LiveData<Response>
        get() {
            return nameMutLiveData
        }

    private val ageMutLiveData: MutableLiveData<Response> = MutableLiveData()

    val ageLiveData: LiveData<Response>
        get() {
            return ageMutLiveData
        }

    private val weightMutLiveData: MutableLiveData<Response> = MutableLiveData()

    val weightLiveData: LiveData<Response>
        get() {
            return weightMutLiveData
        }

    private val timeMutLiveData: MutableLiveData<Response> = MutableLiveData()

    val timeLiveData: LiveData<Response>
        get() {
            return timeMutLiveData
        }

    private val successMutLiveData: MutableLiveData<Response> = MutableLiveData()

    val successLiveData: LiveData<Response>
        get() {
            return successMutLiveData
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

    fun submitName(name: String) {
        if(name.isEmpty())
            nameMutLiveData.value = Error("Please enter name")
        else
            nameMutLiveData.value = Success(name)
    }

    fun submitAge(age: String) {
        if(age.isEmpty())
            ageMutLiveData.value = Error("Please enter age")
        else
            ageMutLiveData.value = Success(age)
    }

    fun submitWeight(weight: String, weightType: String) {
        if(weight.isEmpty())
            weightMutLiveData.value = Error("Please enter your weight")
        else
            weightMutLiveData.value = Success(weight+weightType)
    }

    fun submitExerciseTime(time: String) {
        if(time.isEmpty())
            timeMutLiveData.value = Error("Set your reminder")
        else
            timeMutLiveData.value = Success(time)
    }

    fun success(res: Response) {
        successMutLiveData.value = res
    }
}

class OnBoardDetailsViewModelFactory(val authRepository: AuthRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return OnBoardDetailsViewModel(authRepository) as T
    }
}
