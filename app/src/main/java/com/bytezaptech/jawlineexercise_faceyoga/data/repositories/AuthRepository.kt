package com.bytezaptech.jawlineexercise_faceyoga.data.repositories

import com.bytezaptech.jawlineexercise_faceyoga.data.local_db.SharedPref
import com.bytezaptech.jawlineexercise_faceyoga.di.ActivityScope
import javax.inject.Inject

@ActivityScope
class AuthRepository @Inject constructor(val sharedPref: SharedPref) {
}