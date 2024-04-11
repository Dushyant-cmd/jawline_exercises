package com.bytezaptech.jawlineexercise_faceyoga.data.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bytezaptech.jawlineexercise_faceyoga.data.local_db.SharedPref
import com.bytezaptech.jawlineexercise_faceyoga.data.local_db.room.RoomDb
import com.bytezaptech.jawlineexercise_faceyoga.data.local_db.room.entities.UserEntity
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
    suspend fun signOrSignUpUser(authCredential: AuthCredential, name: String, email: String) {
        authLiveDataMut.value = Progress()
        firestore.collection(Constants.COL_USERS).document(email)
            .get().addOnCompleteListener {
                when (it.isSuccessful) {
                    true -> {
                        val doc = it.result
                        if(doc.exists()) {
                            //user does not exist
                            val map = mapOf("name" to name, "email" to email, "plan" to Constants.FREE_PLAN,
                                "planType" to Constants.FREE_PLAN_TYPE, "lastActiveTimestamp" to System.currentTimeMillis(),
                                "timestamp" to System.currentTimeMillis(), "planStartTimestamp" to System.currentTimeMillis(),
                                "planEndTimestamp" to System.currentTimeMillis())
                            firestore.collection(Constants.COL_USERS).document(email).set(map)
                                .addOnCompleteListener {
                                    when(it.isSuccessful) {
                                        true -> {
                                            val user = UserEntity(0, name, email, Constants.FREE_PLAN, Constants.FREE_PLAN_TYPE)
                                            roomDb.getUserDao().insert(user)
                                            sharedPref.setString(Constants.NAME, user.name!!)
                                            sharedPref.setString(Constants.EMAIL, user.email!!)
                                            sharedPref.setBoolean(Constants.KEY_IS_USER_LOGGED, true)

                                            authLiveDataMut.value = Success("Account created successfully")
                                        }
                                        else -> {
                                            authLiveDataMut.value = Error(it.exception?.message.toString())
                                        }
                                    }
                                }
                        } else {
                            //user exist
                            firebaseAuth.signInWithCredential(authCredential)
                                .addOnCompleteListener {task ->
                                    when(task.isSuccessful) {
                                        true -> {
                                            val doc = it.result
                                            val user: UserEntity = doc.toObject(UserEntity::class.java) as UserEntity
                                            roomDb.getUserDao().insert(user)
                                            sharedPref.setString(Constants.NAME, user.name!!)
                                            sharedPref.setString(Constants.EMAIL, user.email!!)
                                            sharedPref.setBoolean(Constants.KEY_IS_USER_LOGGED, true)

                                            authLiveDataMut.value = Success("Sign-In successfully")
                                        }
                                        else -> {
                                            authLiveDataMut.value = Error(task.exception?.message.toString())
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
}