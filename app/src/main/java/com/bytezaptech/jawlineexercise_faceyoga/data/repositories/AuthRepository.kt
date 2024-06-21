package com.bytezaptech.jawlineexercise_faceyoga.data.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bytezaptech.jawlineexercise_faceyoga.data.local.RoomDb
import com.bytezaptech.jawlineexercise_faceyoga.data.local.SharedPref
import com.bytezaptech.jawlineexercise_faceyoga.data.local.entities.UserEntity
import com.bytezaptech.jawlineexercise_faceyoga.data.local.entities.UserExerciseDetails
import com.bytezaptech.jawlineexercise_faceyoga.di.ActivityScope
import com.bytezaptech.jawlineexercise_faceyoga.utils.Constants
import com.bytezaptech.jawlineexercise_faceyoga.utils.Error
import com.bytezaptech.jawlineexercise_faceyoga.utils.Progress
import com.bytezaptech.jawlineexercise_faceyoga.utils.Response
import com.bytezaptech.jawlineexercise_faceyoga.utils.Success
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject

@ActivityScope
class AuthRepository @Inject constructor(
    val firebaseAuth: FirebaseAuth,
    val firestore: FirebaseFirestore,
    val sharedPref: SharedPref,
    val roomDb: RoomDb
) {
    private val authLiveDataMut: MutableLiveData<Response> = MutableLiveData()
    val authLiveData: LiveData<Response>
        get() {
            return authLiveDataMut
        }

    /**Below method will check if there is any document present with matched email(unique value)
     * if true -> save user details in shared pref and post value in mutable live data with success.
     * else false -> create user in firestore then store user details in shared pref */
    suspend fun signOrSignUpUser(
        authCredential: AuthCredential,
        name: String,
        email: String,
        profile: String
    ) {
        authLiveDataMut.value = Progress()
        firestore.collection(Constants.COL_USERS).document(email)
            .get().addOnCompleteListener {
                when (it.isSuccessful) {
                    true -> {
                        val doc = it.result
                        if (doc.data == null) {
                            //user does not exist
                            val map = mapOf(
                                "name" to name,
                                "email" to email,
                                "plan" to Constants.FREE_PLAN,
                                "planType" to Constants.FREE_PLAN_TYPE,
                                "lastActiveTimestamp" to System.currentTimeMillis(),
                                "timestamp" to System.currentTimeMillis(),
                                "planStartTimestamp" to System.currentTimeMillis(),
                                "planEndTimestamp" to System.currentTimeMillis(),
                                "profile" to profile,
                                "isDetailFilled" to false
                            )
                            firestore.collection(Constants.COL_USERS).document(email).set(map)
                                .addOnCompleteListener {
                                    when (it.isSuccessful) {
                                        true -> {
                                            val user = UserEntity(
                                                0,
                                                name,
                                                email,
                                                profile,
                                                Constants.FREE_PLAN,
                                                Constants.FREE_PLAN_TYPE,
                                                false
                                            )
                                            roomDb.getUserDao().insert(user)
                                            sharedPref.setString(Constants.NAME, user.name!!)
                                            sharedPref.setString(Constants.EMAIL, user.email!!)
                                            sharedPref.setBoolean(Constants.isDetailFilled, false)
                                            sharedPref.setBoolean(
                                                Constants.KEY_IS_USER_LOGGED,
                                                true
                                            )

                                            authLiveDataMut.value =
                                                Success("Account created successfully")
                                        }

                                        else -> {
                                            authLiveDataMut.value =
                                                Error(it.exception?.message.toString())
                                        }
                                    }
                                }
                        } else {
                            //user exist
                            firebaseAuth.signInWithCredential(authCredential)
                                .addOnCompleteListener { task ->
                                    when (task.isSuccessful) {
                                        true -> {
                                            val doc = it.result
                                            val isDetailFilled = doc.getBoolean("isDetailFilled")
                                            val user = UserEntity(
                                                0,
                                                doc.getString("name"),
                                                doc.getString("email"),
                                                doc.getString("profile"),
                                                doc.getString("plan"),
                                                doc.getLong("planType")?.toInt() ?: 0,
                                                isDetailFilled
                                            )
                                            roomDb.getUserDao().insert(user)
                                            sharedPref.setString(Constants.NAME, user.name!!)
                                            sharedPref.setString(Constants.EMAIL, user.email!!)
                                            sharedPref.setBoolean(
                                                Constants.isDetailFilled,
                                                isDetailFilled ?: false
                                            )
                                            sharedPref.setBoolean(
                                                Constants.KEY_IS_USER_LOGGED,
                                                true
                                            )

                                            authLiveDataMut.value = Success("Sign-In successfully")
                                        }

                                        else -> {
                                            authLiveDataMut.value =
                                                Error(task.exception?.message.toString())
                                        }
                                    }
                                }
                        }
                    }

                    else -> {
                        authLiveDataMut.value = Error(it.exception?.message.toString())
                    }
                }
            }
    }

    suspend fun updateUserDetails(userExerciseDetails: UserExerciseDetails) {
        sharedPref.setBoolean(Constants.KEY_IS_USER_LOGGED, true)
        roomDb.getExerciseDetailsDao().deleteAll()
        roomDb.getExerciseDetailsDao().insert(userExerciseDetails)
        sharedPref.setBoolean(Constants.isDetailFilled, true)
        val email = sharedPref.getString(Constants.EMAIL)
        firestore.collection(Constants.COL_USERS).document(email ?: "")
            .update(mapOf("isDetailFilled" to true))
            .addOnCompleteListener {
            }
    }
}