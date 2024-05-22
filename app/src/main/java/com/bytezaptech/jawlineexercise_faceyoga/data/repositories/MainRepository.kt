package com.bytezaptech.jawlineexercise_faceyoga.data.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bytezaptech.jawlineexercise_faceyoga.data.local.RoomDb
import com.bytezaptech.jawlineexercise_faceyoga.data.local.SharedPref
import com.bytezaptech.jawlineexercise_faceyoga.data.local.entities.ExerciseChallenge
import com.bytezaptech.jawlineexercise_faceyoga.utils.Constants
import com.bytezaptech.jawlineexercise_faceyoga.utils.Error
import com.bytezaptech.jawlineexercise_faceyoga.utils.Progress
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

    private var exerciseChallengeMut = MutableLiveData<Response>()
    val exerciseChallenge: LiveData<Response>
        get() {
            return exerciseChallengeMut
        }

    fun isUserLoggedIn() {
        sharedPref.getBoolean(Constants.KEY_IS_USER_LOGGED).apply {
            splashAuthLiveDataMut.value = Success(this)
        }
    }

    fun getUserDetails() {
        userProfileData = Success(roomDb.getUserDao().getUser())
    }

    fun addExerciseChallenges() {
        if(roomDb.getExerciseChallengeDao().getAll().isNotEmpty()) {
            exerciseChallengeMut.value = Progress()

            val thirtyDays = ExerciseChallenge(0, "30 Days", 0, 30, false)
            val sixtyDays = ExerciseChallenge(0, "60 Days", 0, 60, false)
            val oneTwentyDays = ExerciseChallenge(0, "120 Days", 0, 120, false)

            roomDb.getExerciseChallengeDao().insert(thirtyDays)
            roomDb.getExerciseChallengeDao().insert(sixtyDays)
            roomDb.getExerciseChallengeDao().insert(oneTwentyDays)

            exerciseChallengeMut.value = Success(roomDb.getExerciseChallengeDao().getAll())
        }
    }
}