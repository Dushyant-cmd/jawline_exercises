package com.bytezaptech.jawlineexercise_faceyoga.data.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bytezaptech.jawlineexercise_faceyoga.data.local.RoomDb
import com.bytezaptech.jawlineexercise_faceyoga.data.local.SharedPref
import com.bytezaptech.jawlineexercise_faceyoga.utils.Constants
import com.bytezaptech.jawlineexercise_faceyoga.utils.Error
import com.bytezaptech.jawlineexercise_faceyoga.utils.Response
import com.bytezaptech.jawlineexercise_faceyoga.utils.Success
import javax.inject.Inject

class MainRepository @Inject constructor(private val roomDb: RoomDb, private val sharedPref: SharedPref) {
    private val splashAuthLiveDataMut: MutableLiveData<Response> = MutableLiveData()
    val splashAuthLiveData: LiveData<Response>
        get() {
            return splashAuthLiveDataMut
        }

    private var userProfileData: Response = Error("Something went wrong")
    val userProfile: Response
        get() {
            return userProfileData
        }

    fun isUserLoggedIn() {
        sharedPref.getBoolean(Constants.KEY_IS_USER_LOGGED).apply {
            splashAuthLiveDataMut.value = Success(this)
        }
    }

    fun getUserDetails() {
        userProfileData = Success(roomDb.getUserDao().getUser())
    }
}