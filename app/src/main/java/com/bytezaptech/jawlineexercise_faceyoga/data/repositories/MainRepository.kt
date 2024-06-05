package com.bytezaptech.jawlineexercise_faceyoga.data.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bytezaptech.jawlineexercise_faceyoga.R
import com.bytezaptech.jawlineexercise_faceyoga.data.local.RoomDb
import com.bytezaptech.jawlineexercise_faceyoga.data.local.SharedPref
import com.bytezaptech.jawlineexercise_faceyoga.data.local.entities.ExerciseChallenge
import com.bytezaptech.jawlineexercise_faceyoga.data.local.entities.OneTwentyDaysExerciseEntity
import com.bytezaptech.jawlineexercise_faceyoga.data.local.entities.SixtyDaysExerciseEntity
import com.bytezaptech.jawlineexercise_faceyoga.data.local.entities.ThirtyDaysExerciseEntity
import com.bytezaptech.jawlineexercise_faceyoga.models.EachDayExerciseModel
import com.bytezaptech.jawlineexercise_faceyoga.utils.Constants
import com.bytezaptech.jawlineexercise_faceyoga.utils.Error
import com.bytezaptech.jawlineexercise_faceyoga.utils.ExerciseResponse
import com.bytezaptech.jawlineexercise_faceyoga.utils.ExerciseSuccess
import com.bytezaptech.jawlineexercise_faceyoga.utils.Response
import com.bytezaptech.jawlineexercise_faceyoga.utils.Success
import javax.inject.Inject

class MainRepository @Inject constructor(
    private val roomDb: RoomDb,
    private val sharedPref: SharedPref
) {
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

    private var exerciseDay: MutableLiveData<ExerciseResponse> = MutableLiveData()

    val exerciseDayLiveData: LiveData<ExerciseResponse>
        get() {
            return exerciseDay
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
        if (roomDb.getExerciseChallengeDao().getAll().isEmpty()) {

            val thirtyDays = ExerciseChallenge(0, "30 Days", 1, 30, false)
            val sixtyDays = ExerciseChallenge(1, "60 Days", 1, 60, false)
            val oneTwentyDays = ExerciseChallenge(2, "120 Days", 1, 120, false)

            roomDb.getExerciseChallengeDao().insert(thirtyDays)
            roomDb.getExerciseChallengeDao().insert(sixtyDays)
            roomDb.getExerciseChallengeDao().insert(oneTwentyDays)
        }

        exerciseChallengeMut.value = Success(roomDb.getExerciseChallengeDao().getAll())
    }

    fun getThirtyDayExercise(day: String) {
        exerciseDay.value =
            ExerciseSuccess(roomDb.getThirtyDaysDao().getExerciseByDay(day).exercises, day)
    }

    fun getSixtyDayExercise(day: String) {
        exerciseDay.value =
            ExerciseSuccess(roomDb.getSixtyDaysDao().getExerciseByDay(day).exercises, day)
    }

    fun getOneTwentyDayExercise(day: String) {
        exerciseDay.value =
            ExerciseSuccess(roomDb.getOneTwentyDaysDao().getExerciseByDay(day).exercises, day)
    }

    fun addAllExerciseDays() {
        if (!roomDb.getThirtyDaysDao().getExercises().isEmpty()) {
            val dayExercise1 =
                EachDayExerciseModel("1", R.raw.a_one, "Upside down", "45", "This is a exercise")
            val dayExercise2 =
                EachDayExerciseModel("2", R.raw.a_one, "Upside down", "45", "This is a exercise")
            val dayExercise3 =
                EachDayExerciseModel("3", R.raw.a_one, "Upside down", "45", "This is a exercise")
            val dayExercise4 =
                EachDayExerciseModel("4", R.raw.a_one, "Upside down", "45", "This is a exercise")
            val dayExercise5 =
                EachDayExerciseModel("4", R.raw.a_one, "Upside down", "45", "This is a exercise")
            val dayExercise6 =
                EachDayExerciseModel("5", R.raw.a_one, "Upside down", "45", "This is a exercise")
            val dayExercise7 =
                EachDayExerciseModel("6", R.raw.a_one, "Upside down", "45", "This is a exercise")
            val dayExercise8 =
                EachDayExerciseModel("7", R.raw.a_one, "Upside down", "45", "This is a exercise")
            val dayExercise9 =
                EachDayExerciseModel("8", R.raw.a_one, "Upside down", "45", "This is a exercise")
            val dayExercise10 =
                EachDayExerciseModel("8", R.raw.a_one, "Upside down", "45", "This is a exercise")
            val dayExercise11 =
                EachDayExerciseModel("10", R.raw.a_one, "Upside down", "45", "This is a exercise")
            val dayExercise12 =
                EachDayExerciseModel("12", R.raw.a_one, "Upside down", "45", "This is a exercise")
            val dayExercise13 =
                EachDayExerciseModel("13", R.raw.a_one, "Upside down", "45", "This is a exercise")
            val dayExercise14 =
                EachDayExerciseModel("14", R.raw.a_one, "Upside down", "45", "This is a exercise")
            val dayExercise15 =
                EachDayExerciseModel("15", R.raw.a_one, "Upside down", "45", "This is a exercise")
            val dayExercise16 =
                EachDayExerciseModel("16", R.raw.a_one, "Upside down", "45", "This is a exercise")
            val dayExercise17 =
                EachDayExerciseModel("17", R.raw.a_one, "Upside down", "45", "This is a exercise")
            val dayExercise18 =
                EachDayExerciseModel("18", R.raw.a_one, "Upside down", "45", "This is a exercise")
            val dayExercise19 =
                EachDayExerciseModel("19", R.raw.a_one, "Upside down", "45", "This is a exercise")
            val dayExercise20 =
                EachDayExerciseModel("20", R.raw.a_one, "Upside down", "45", "This is a exercise")
            val dayExercise21 =
                EachDayExerciseModel("21", R.raw.a_one, "Upside down", "45", "This is a exercise")
            val dayExercise22 =
                EachDayExerciseModel("22", R.raw.a_one, "Upside down", "45", "This is a exercise")
            val dayExercise23 =
                EachDayExerciseModel("23", R.raw.a_one, "Upside down", "45", "This is a exercise")
            val dayExercise24 =
                EachDayExerciseModel("24", R.raw.a_one, "Upside down", "45", "This is a exercise")
            val dayExercise25 =
                EachDayExerciseModel("25", R.raw.a_one, "Upside down", "45", "This is a exercise")
            val dayExercise26 =
                EachDayExerciseModel("26", R.raw.a_one, "Upside down", "45", "This is a exercise")
            val dayExercise27 =
                EachDayExerciseModel("27", R.raw.a_one, "Upside down", "45", "This is a exercise")
            val dayExercise28 =
                EachDayExerciseModel("28", R.raw.a_one, "Upside down", "45", "This is a exercise")
            val dayExercise29 =
                EachDayExerciseModel("29", R.raw.a_one, "Upside down", "45", "This is a exercise")
            val dayExercise30 =
                EachDayExerciseModel("30", R.raw.a_one, "Upside down", "45", "This is a exercise")

            val list = listOf(
                ThirtyDaysExerciseEntity(
                    0,
                    "1",
                    listOf(
                        dayExercise1,
                        dayExercise2,
                        dayExercise3,
                        dayExercise4,
                        dayExercise5,
                        dayExercise6,
                        dayExercise7,
                        dayExercise8,
                        dayExercise9,
                        dayExercise10,
                        dayExercise11,
                        dayExercise12,
                        dayExercise13,
                        dayExercise14,
                        dayExercise15,
                        dayExercise16,
                        dayExercise17,
                        dayExercise18,
                        dayExercise19,
                        dayExercise20,
                        dayExercise21,
                        dayExercise22,
                        dayExercise23,
                        dayExercise24,
                        dayExercise25,
                        dayExercise26,
                        dayExercise27,
                        dayExercise28,
                        dayExercise29,
                        dayExercise30
                    )
                )
            )

            roomDb.getThirtyDaysDao().insertAll(list)
        }
        if (!roomDb.getSixtyDaysDao().getExercises().isEmpty()) {
            val dayExercise1 =
                EachDayExerciseModel("1", R.raw.a_one, "Upside down", "45", "This is a exercise")
            val dayExercise2 =
                EachDayExerciseModel("2", R.raw.a_one, "Upside down", "45", "This is a exercise")
            val dayExercise3 =
                EachDayExerciseModel("3", R.raw.a_one, "Upside down", "45", "This is a exercise")
            val dayExercise4 =
                EachDayExerciseModel("4", R.raw.a_one, "Upside down", "45", "This is a exercise")
            val dayExercise5 =
                EachDayExerciseModel("4", R.raw.a_one, "Upside down", "45", "This is a exercise")
            val dayExercise6 =
                EachDayExerciseModel("5", R.raw.a_one, "Upside down", "45", "This is a exercise")
            val dayExercise7 =
                EachDayExerciseModel("6", R.raw.a_one, "Upside down", "45", "This is a exercise")
            val dayExercise8 =
                EachDayExerciseModel("7", R.raw.a_one, "Upside down", "45", "This is a exercise")
            val dayExercise9 =
                EachDayExerciseModel("8", R.raw.a_one, "Upside down", "45", "This is a exercise")
            val dayExercise10 =
                EachDayExerciseModel("8", R.raw.a_one, "Upside down", "45", "This is a exercise")
            val dayExercise11 =
                EachDayExerciseModel("10", R.raw.a_one, "Upside down", "45", "This is a exercise")
            val dayExercise12 =
                EachDayExerciseModel("12", R.raw.a_one, "Upside down", "45", "This is a exercise")
            val dayExercise13 =
                EachDayExerciseModel("13", R.raw.a_one, "Upside down", "45", "This is a exercise")
            val dayExercise14 =
                EachDayExerciseModel("14", R.raw.a_one, "Upside down", "45", "This is a exercise")
            val dayExercise15 =
                EachDayExerciseModel("15", R.raw.a_one, "Upside down", "45", "This is a exercise")
            val dayExercise16 =
                EachDayExerciseModel("16", R.raw.a_one, "Upside down", "45", "This is a exercise")
            val dayExercise17 =
                EachDayExerciseModel("17", R.raw.a_one, "Upside down", "45", "This is a exercise")
            val dayExercise18 =
                EachDayExerciseModel("18", R.raw.a_one, "Upside down", "45", "This is a exercise")
            val dayExercise19 =
                EachDayExerciseModel("19", R.raw.a_one, "Upside down", "45", "This is a exercise")
            val dayExercise20 =
                EachDayExerciseModel("20", R.raw.a_one, "Upside down", "45", "This is a exercise")
            val dayExercise21 =
                EachDayExerciseModel("21", R.raw.a_one, "Upside down", "45", "This is a exercise")
            val dayExercise22 =
                EachDayExerciseModel("22", R.raw.a_one, "Upside down", "45", "This is a exercise")
            val dayExercise23 =
                EachDayExerciseModel("23", R.raw.a_one, "Upside down", "45", "This is a exercise")
            val dayExercise24 =
                EachDayExerciseModel("24", R.raw.a_one, "Upside down", "45", "This is a exercise")
            val dayExercise25 =
                EachDayExerciseModel("25", R.raw.a_one, "Upside down", "45", "This is a exercise")
            val dayExercise26 =
                EachDayExerciseModel("26", R.raw.a_one, "Upside down", "45", "This is a exercise")
            val dayExercise27 =
                EachDayExerciseModel("27", R.raw.a_one, "Upside down", "45", "This is a exercise")
            val dayExercise28 =
                EachDayExerciseModel("28", R.raw.a_one, "Upside down", "45", "This is a exercise")
            val dayExercise29 =
                EachDayExerciseModel("29", R.raw.a_one, "Upside down", "45", "This is a exercise")
            val dayExercise30 =
                EachDayExerciseModel("30", R.raw.a_one, "Upside down", "45", "This is a exercise")
            val dayExercise31 =
                EachDayExerciseModel("31", R.raw.a_one, "Upside down", "45", "This is a exercise")
            val dayExercise32 =
                EachDayExerciseModel("32", R.raw.a_one, "Upside down", "45", "This is a exercise")
            val dayExercise33 =
                EachDayExerciseModel("33", R.raw.a_one, "Upside down", "45", "This is a exercise")
            val dayExercise34 =
                EachDayExerciseModel("34", R.raw.a_one, "Upside down", "45", "This is a exercise")
            val dayExercise35 =
                EachDayExerciseModel("35", R.raw.a_one, "Upside down", "45", "This is a exercise")
            val dayExercise36 =
                EachDayExerciseModel("36", R.raw.a_one, "Upside down", "45", "This is a exercise")
            val dayExercise37 =
                EachDayExerciseModel("37", R.raw.a_one, "Upside down", "45", "This is a exercise")
            val dayExercise38 =
                EachDayExerciseModel("38", R.raw.a_one, "Upside down", "45", "This is a exercise")
            val dayExercise39 =
                EachDayExerciseModel("39", R.raw.a_one, "Upside down", "45", "This is a exercise")
            val dayExercise40 =
                EachDayExerciseModel("40", R.raw.a_one, "Upside down", "45", "This is a exercise")
            val dayExercise41 =
                EachDayExerciseModel("41", R.raw.a_one, "Upside down", "45", "This is a exercise")
            val dayExercise42 =
                EachDayExerciseModel("42", R.raw.a_one, "Upside down", "45", "This is a exercise")
            val dayExercise43 =
                EachDayExerciseModel("43", R.raw.a_one, "Upside down", "45", "This is a exercise")
            val dayExercise44 =
                EachDayExerciseModel("44", R.raw.a_one, "Upside down", "45", "This is a exercise")
            val dayExercise45 =
                EachDayExerciseModel("45", R.raw.a_one, "Upside down", "45", "This is a exercise")
            val dayExercise46 =
                EachDayExerciseModel("46", R.raw.a_one, "Upside down", "45", "This is a exercise")
            val dayExercise47 =
                EachDayExerciseModel("47", R.raw.a_one, "Upside down", "45", "This is a exercise")
            val dayExercise48 =
                EachDayExerciseModel("48", R.raw.a_one, "Upside down", "45", "This is a exercise")
            val dayExercise49 =
                EachDayExerciseModel("49", R.raw.a_one, "Upside down", "45", "This is a exercise")
            val dayExercise50 =
                EachDayExerciseModel("50", R.raw.a_one, "Upside down", "45", "This is a exercise")
            val dayExercise51 =
                EachDayExerciseModel("51", R.raw.a_one, "Upside down", "45", "This is a exercise")
            val dayExercise52 =
                EachDayExerciseModel("52", R.raw.a_one, "Upside down", "45", "This is a exercise")
            val dayExercise53 =
                EachDayExerciseModel("53", R.raw.a_one, "Upside down", "45", "This is a exercise")
            val dayExercise54 =
                EachDayExerciseModel("54", R.raw.a_one, "Upside down", "45", "This is a exercise")
            val dayExercise55 =
                EachDayExerciseModel("55", R.raw.a_one, "Upside down", "45", "This is a exercise")
            val dayExercise56 =
                EachDayExerciseModel("56", R.raw.a_one, "Upside down", "45", "This is a exercise")
            val dayExercise57 =
                EachDayExerciseModel("57", R.raw.a_one, "Upside down", "45", "This is a exercise")
            val dayExercise58 =
                EachDayExerciseModel("58", R.raw.a_one, "Upside down", "45", "This is a exercise")
            val dayExercise59 =
                EachDayExerciseModel("59", R.raw.a_one, "Upside down", "45", "This is a exercise")
            val dayExercise60 =
                EachDayExerciseModel("60", R.raw.a_one, "Upside down", "45", "This is a exercise")

            val list = listOf(
                SixtyDaysExerciseEntity(
                    0,
                    "1",
                    listOf(
                        dayExercise1,
                        dayExercise2,
                        dayExercise3,
                        dayExercise4,
                        dayExercise5,
                        dayExercise6,
                        dayExercise7,
                        dayExercise8,
                        dayExercise9,
                        dayExercise10,
                        dayExercise11,
                        dayExercise12,
                        dayExercise13,
                        dayExercise14,
                        dayExercise15,
                        dayExercise16,
                        dayExercise17,
                        dayExercise18,
                        dayExercise19,
                        dayExercise20,
                        dayExercise21,
                        dayExercise22,
                        dayExercise23,
                        dayExercise24,
                        dayExercise25,
                        dayExercise26,
                        dayExercise27,
                        dayExercise28,
                        dayExercise29,
                        dayExercise30,
                        dayExercise31,
                        dayExercise32,
                        dayExercise33,
                        dayExercise34,
                        dayExercise35,
                        dayExercise36,
                        dayExercise37,
                        dayExercise38,
                        dayExercise39,
                        dayExercise40,
                        dayExercise41,
                        dayExercise42,
                        dayExercise43,
                        dayExercise44,
                        dayExercise45,
                        dayExercise46,
                        dayExercise47,
                        dayExercise48,
                        dayExercise49,
                        dayExercise50,
                        dayExercise51,
                        dayExercise52,
                        dayExercise53,
                        dayExercise54,
                        dayExercise55,
                        dayExercise56,
                        dayExercise57,
                        dayExercise58,
                        dayExercise59,
                        dayExercise60
                    )
                )
            )

            roomDb.getSixtyDaysDao().insertAll(list)
        }
        if (!roomDb.getOneTwentyDaysDao().getDetails().isEmpty()) {
            val dayExercise1 =
                EachDayExerciseModel("1", R.raw.a_one, "Upside down", "45", "This is a exercise")
            val dayExercise2 =
                EachDayExerciseModel("2", R.raw.a_one, "Upside down", "45", "This is a exercise")
            val dayExercise3 =
                EachDayExerciseModel("3", R.raw.a_one, "Upside down", "45", "This is a exercise")
            val dayExercise4 =
                EachDayExerciseModel("4", R.raw.a_one, "Upside down", "45", "This is a exercise")
            val dayExercise5 =
                EachDayExerciseModel("4", R.raw.a_one, "Upside down", "45", "This is a exercise")
            val dayExercise6 =
                EachDayExerciseModel("5", R.raw.a_one, "Upside down", "45", "This is a exercise")
            val dayExercise7 =
                EachDayExerciseModel("6", R.raw.a_one, "Upside down", "45", "This is a exercise")
            val dayExercise8 =
                EachDayExerciseModel("7", R.raw.a_one, "Upside down", "45", "This is a exercise")
            val dayExercise9 =
                EachDayExerciseModel("8", R.raw.a_one, "Upside down", "45", "This is a exercise")
            val dayExercise10 =
                EachDayExerciseModel("8", R.raw.a_one, "Upside down", "45", "This is a exercise")
            val dayExercise11 =
                EachDayExerciseModel("10", R.raw.a_one, "Upside down", "45", "This is a exercise")
            val dayExercise12 =
                EachDayExerciseModel("12", R.raw.a_one, "Upside down", "45", "This is a exercise")
            val dayExercise13 =
                EachDayExerciseModel("13", R.raw.a_one, "Upside down", "45", "This is a exercise")
            val dayExercise14 =
                EachDayExerciseModel("14", R.raw.a_one, "Upside down", "45", "This is a exercise")
            val dayExercise15 =
                EachDayExerciseModel("15", R.raw.a_one, "Upside down", "45", "This is a exercise")
            val dayExercise16 =
                EachDayExerciseModel("16", R.raw.a_one, "Upside down", "45", "This is a exercise")
            val dayExercise17 =
                EachDayExerciseModel("17", R.raw.a_one, "Upside down", "45", "This is a exercise")
            val dayExercise18 =
                EachDayExerciseModel("18", R.raw.a_one, "Upside down", "45", "This is a exercise")
            val dayExercise19 =
                EachDayExerciseModel("19", R.raw.a_one, "Upside down", "45", "This is a exercise")
            val dayExercise20 =
                EachDayExerciseModel("20", R.raw.a_one, "Upside down", "45", "This is a exercise")
            val dayExercise21 =
                EachDayExerciseModel("21", R.raw.a_one, "Upside down", "45", "This is a exercise")
            val dayExercise22 =
                EachDayExerciseModel("22", R.raw.a_one, "Upside down", "45", "This is a exercise")
            val dayExercise23 =
                EachDayExerciseModel("23", R.raw.a_one, "Upside down", "45", "This is a exercise")
            val dayExercise24 =
                EachDayExerciseModel("24", R.raw.a_one, "Upside down", "45", "This is a exercise")
            val dayExercise25 =
                EachDayExerciseModel("25", R.raw.a_one, "Upside down", "45", "This is a exercise")
            val dayExercise26 =
                EachDayExerciseModel("26", R.raw.a_one, "Upside down", "45", "This is a exercise")
            val dayExercise27 =
                EachDayExerciseModel("27", R.raw.a_one, "Upside down", "45", "This is a exercise")
            val dayExercise28 =
                EachDayExerciseModel("28", R.raw.a_one, "Upside down", "45", "This is a exercise")
            val dayExercise29 =
                EachDayExerciseModel("29", R.raw.a_one, "Upside down", "45", "This is a exercise")
            val dayExercise30 =
                EachDayExerciseModel("30", R.raw.a_one, "Upside down", "45", "This is a exercise")
            val dayExercise31 =
                EachDayExerciseModel("31", R.raw.a_one, "Upside down", "45", "This is a exercise")
            val dayExercise32 =
                EachDayExerciseModel("32", R.raw.a_one, "Upside down", "45", "This is a exercise")
            val dayExercise33 =
                EachDayExerciseModel("33", R.raw.a_one, "Upside down", "45", "This is a exercise")
            val dayExercise34 =
                EachDayExerciseModel("34", R.raw.a_one, "Upside down", "45", "This is a exercise")
            val dayExercise35 =
                EachDayExerciseModel("35", R.raw.a_one, "Upside down", "45", "This is a exercise")
            val dayExercise36 =
                EachDayExerciseModel("36", R.raw.a_one, "Upside down", "45", "This is a exercise")
            val dayExercise37 =
                EachDayExerciseModel("37", R.raw.a_one, "Upside down", "45", "This is a exercise")
            val dayExercise38 =
                EachDayExerciseModel("38", R.raw.a_one, "Upside down", "45", "This is a exercise")
            val dayExercise39 =
                EachDayExerciseModel("39", R.raw.a_one, "Upside down", "45", "This is a exercise")
            val dayExercise40 =
                EachDayExerciseModel("40", R.raw.a_one, "Upside down", "45", "This is a exercise")
            val dayExercise41 =
                EachDayExerciseModel("41", R.raw.a_one, "Upside down", "45", "This is a exercise")
            val dayExercise42 =
                EachDayExerciseModel("42", R.raw.a_one, "Upside down", "45", "This is a exercise")
            val dayExercise43 =
                EachDayExerciseModel("43", R.raw.a_one, "Upside down", "45", "This is a exercise")
            val dayExercise44 =
                EachDayExerciseModel("44", R.raw.a_one, "Upside down", "45", "This is a exercise")
            val dayExercise45 =
                EachDayExerciseModel("45", R.raw.a_one, "Upside down", "45", "This is a exercise")
            val dayExercise46 =
                EachDayExerciseModel("46", R.raw.a_one, "Upside down", "45", "This is a exercise")
            val dayExercise47 =
                EachDayExerciseModel("47", R.raw.a_one, "Upside down", "45", "This is a exercise")
            val dayExercise48 =
                EachDayExerciseModel("48", R.raw.a_one, "Upside down", "45", "This is a exercise")
            val dayExercise49 =
                EachDayExerciseModel("49", R.raw.a_one, "Upside down", "45", "This is a exercise")
            val dayExercise50 =
                EachDayExerciseModel("50", R.raw.a_one, "Upside down", "45", "This is a exercise")
            val dayExercise51 =
                EachDayExerciseModel("51", R.raw.a_one, "Upside down", "45", "This is a exercise")
            val dayExercise52 =
                EachDayExerciseModel("52", R.raw.a_one, "Upside down", "45", "This is a exercise")
            val dayExercise53 =
                EachDayExerciseModel("53", R.raw.a_one, "Upside down", "45", "This is a exercise")
            val dayExercise54 =
                EachDayExerciseModel("54", R.raw.a_one, "Upside down", "45", "This is a exercise")
            val dayExercise55 =
                EachDayExerciseModel("55", R.raw.a_one, "Upside down", "45", "This is a exercise")
            val dayExercise56 =
                EachDayExerciseModel("56", R.raw.a_one, "Upside down", "45", "This is a exercise")
            val dayExercise57 =
                EachDayExerciseModel("57", R.raw.a_one, "Upside down", "45", "This is a exercise")
            val dayExercise58 =
                EachDayExerciseModel("58", R.raw.a_one, "Upside down", "45", "This is a exercise")
            val dayExercise59 =
                EachDayExerciseModel("59", R.raw.a_one, "Upside down", "45", "This is a exercise")
            val dayExercise60 =
                EachDayExerciseModel("60", R.raw.a_one, "Upside down", "45", "This is a exercise")
            val dayExercise61 =
                EachDayExerciseModel("61", R.raw.a_one, "Upside down", "45", "This is a exercise")
            val dayExercise62 =
                EachDayExerciseModel("62", R.raw.a_one, "Upside down", "45", "This is a exercise")
            val dayExercise63 =
                EachDayExerciseModel("63", R.raw.a_one, "Upside down", "45", "This is a exercise")
            val dayExercise64 =
                EachDayExerciseModel("64", R.raw.a_one, "Upside down", "45", "This is a exercise")
            val dayExercise65 =
                EachDayExerciseModel("65", R.raw.a_one, "Upside down", "45", "This is a exercise")
            val dayExercise66 =
                EachDayExerciseModel("66", R.raw.a_one, "Upside down", "45", "This is a exercise")
            val dayExercise67 =
                EachDayExerciseModel("67", R.raw.a_one, "Upside down", "45", "This is a exercise")
            val dayExercise68 =
                EachDayExerciseModel("68", R.raw.a_one, "Upside down", "45", "This is a exercise")
            val dayExercise69 =
                EachDayExerciseModel("69", R.raw.a_one, "Upside down", "45", "This is a exercise")
            val dayExercise70 =
                EachDayExerciseModel("70", R.raw.a_one, "Upside down", "45", "This is a exercise")
            val dayExercise71 =
                EachDayExerciseModel("71", R.raw.a_one, "Upside down", "45", "This is a exercise")
            val dayExercise72 =
                EachDayExerciseModel("72", R.raw.a_one, "Upside down", "45", "This is an exercise")
            val dayExercise73 =
                EachDayExerciseModel("73", R.raw.a_one, "Upside down", "45", "This is an exercise")
            val dayExercise74 =
                EachDayExerciseModel("74", R.raw.a_one, "Upside down", "45", "This is an exercise")
            val dayExercise75 =
                EachDayExerciseModel("75", R.raw.a_one, "Upside down", "45", "This is an exercise")
            val dayExercise76 =
                EachDayExerciseModel("76", R.raw.a_one, "Upside down", "45", "This is an exercise")
            val dayExercise77 =
                EachDayExerciseModel("77", R.raw.a_one, "Upside down", "45", "This is an exercise")
            val dayExercise78 =
                EachDayExerciseModel("78", R.raw.a_one, "Upside down", "45", "This is an exercise")
            val dayExercise79 =
                EachDayExerciseModel("79", R.raw.a_one, "Upside down", "45", "This is an exercise")
            val dayExercise80 =
                EachDayExerciseModel("80", R.raw.a_one, "Upside down", "45", "This is an exercise")
            val dayExercise81 =
                EachDayExerciseModel("81", R.raw.a_one, "Upside down", "45", "This is an exercise")
            val dayExercise82 =
                EachDayExerciseModel("82", R.raw.a_one, "Upside down", "45", "This is an exercise")
            val dayExercise83 =
                EachDayExerciseModel("83", R.raw.a_one, "Upside down", "45", "This is an exercise")
            val dayExercise84 =
                EachDayExerciseModel("84", R.raw.a_one, "Upside down", "45", "This is an exercise")
            val dayExercise85 =
                EachDayExerciseModel("85", R.raw.a_one, "Upside down", "45", "This is an exercise")
            val dayExercise86 =
                EachDayExerciseModel("86", R.raw.a_one, "Upside down", "45", "This is an exercise")
            val dayExercise87 =
                EachDayExerciseModel("87", R.raw.a_one, "Upside down", "45", "This is an exercise")
            val dayExercise88 =
                EachDayExerciseModel("88", R.raw.a_one, "Upside down", "45", "This is an exercise")
            val dayExercise89 =
                EachDayExerciseModel("89", R.raw.a_one, "Upside down", "45", "This is an exercise")
            val dayExercise90 =
                EachDayExerciseModel("90", R.raw.a_one, "Upside down", "45", "This is an exercise")
            val dayExercise91 =
                EachDayExerciseModel("91", R.raw.a_one, "Upside down", "45", "This is an exercise")
            val dayExercise92 =
                EachDayExerciseModel("92", R.raw.a_one, "Upside down", "45", "This is an exercise")
            val dayExercise93 =
                EachDayExerciseModel("93", R.raw.a_one, "Upside down", "45", "This is an exercise")
            val dayExercise94 =
                EachDayExerciseModel("94", R.raw.a_one, "Upside down", "45", "This is an exercise")
            val dayExercise95 =
                EachDayExerciseModel("95", R.raw.a_one, "Upside down", "45", "This is an exercise")
            val dayExercise96 =
                EachDayExerciseModel("96", R.raw.a_one, "Upside down", "45", "This is an exercise")
            val dayExercise97 =
                EachDayExerciseModel("97", R.raw.a_one, "Upside down", "45", "This is an exercise")
            val dayExercise98 =
                EachDayExerciseModel("98", R.raw.a_one, "Upside down", "45", "This is an exercise")
            val dayExercise99 =
                EachDayExerciseModel("99", R.raw.a_one, "Upside down", "45", "This is an exercise")
            val dayExercise100 =
                EachDayExerciseModel("100", R.raw.a_one, "Upside down", "45", "This is an exercise")
            val dayExercise101 =
                EachDayExerciseModel("101", R.raw.a_one, "Upside down", "45", "This is an exercise")
            val dayExercise102 =
                EachDayExerciseModel("102", R.raw.a_one, "Upside down", "45", "This is an exercise")
            val dayExercise103 =
                EachDayExerciseModel("103", R.raw.a_one, "Upside down", "45", "This is an exercise")
            val dayExercise104 =
                EachDayExerciseModel("104", R.raw.a_one, "Upside down", "45", "This is an exercise")
            val dayExercise105 =
                EachDayExerciseModel("105", R.raw.a_one, "Upside down", "45", "This is an exercise")
            val dayExercise106 =
                EachDayExerciseModel("106", R.raw.a_one, "Upside down", "45", "This is an exercise")
            val dayExercise107 =
                EachDayExerciseModel("107", R.raw.a_one, "Upside down", "45", "This is an exercise")
            val dayExercise108 =
                EachDayExerciseModel("108", R.raw.a_one, "Upside down", "45", "This is an exercise")
            val dayExercise109 =
                EachDayExerciseModel("109", R.raw.a_one, "Upside down", "45", "This is an exercise")
            val dayExercise110 =
                EachDayExerciseModel("110", R.raw.a_one, "Upside down", "45", "This is an exercise")
            val dayExercise111 =
                EachDayExerciseModel("111", R.raw.a_one, "Upside down", "45", "This is an exercise")
            val dayExercise112 =
                EachDayExerciseModel("112", R.raw.a_one, "Upside down", "45", "This is an exercise")
            val dayExercise113 =
                EachDayExerciseModel("113", R.raw.a_one, "Upside down", "45", "This is an exercise")
            val dayExercise114 =
                EachDayExerciseModel("114", R.raw.a_one, "Upside down", "45", "This is an exercise")
            val dayExercise115 =
                EachDayExerciseModel("115", R.raw.a_one, "Upside down", "45", "This is an exercise")
            val dayExercise116 =
                EachDayExerciseModel("116", R.raw.a_one, "Upside down", "45", "This is an exercise")
            val dayExercise117 =
                EachDayExerciseModel("117", R.raw.a_one, "Upside down", "45", "This is an exercise")
            val dayExercise118 =
                EachDayExerciseModel("118", R.raw.a_one, "Upside down", "45", "This is an exercise")
            val dayExercise119 =
                EachDayExerciseModel("119", R.raw.a_one, "Upside down", "45", "This is an exercise")
            val dayExercise120 =
                EachDayExerciseModel("120", R.raw.a_one, "Upside down", "45", "This is an exercise")


            val list = listOf(
                OneTwentyDaysExerciseEntity(
                    0, "1", listOf(
                        dayExercise1,
                        dayExercise2,
                        dayExercise3,
                        dayExercise4,
                        dayExercise5,
                        dayExercise6,
                        dayExercise7,
                        dayExercise8,
                        dayExercise9,
                        dayExercise10,
                        dayExercise11,
                        dayExercise12,
                        dayExercise13,
                        dayExercise14,
                        dayExercise15,
                        dayExercise16,
                        dayExercise17,
                        dayExercise18,
                        dayExercise19,
                        dayExercise20,
                        dayExercise21,
                        dayExercise22,
                        dayExercise23,
                        dayExercise24,
                        dayExercise25,
                        dayExercise26,
                        dayExercise27,
                        dayExercise28,
                        dayExercise29,
                        dayExercise30,
                        dayExercise31,
                        dayExercise32,
                        dayExercise33,
                        dayExercise34,
                        dayExercise35,
                        dayExercise36,
                        dayExercise37,
                        dayExercise38,
                        dayExercise39,
                        dayExercise40,
                        dayExercise41,
                        dayExercise42,
                        dayExercise43,
                        dayExercise44,
                        dayExercise45,
                        dayExercise46,
                        dayExercise47,
                        dayExercise48,
                        dayExercise49,
                        dayExercise50,
                        dayExercise51,
                        dayExercise52,
                        dayExercise53,
                        dayExercise54,
                        dayExercise55,
                        dayExercise56,
                        dayExercise57,
                        dayExercise58,
                        dayExercise59,
                        dayExercise60,
                        dayExercise61,
                        dayExercise62,
                        dayExercise63,
                        dayExercise64,
                        dayExercise65,
                        dayExercise66,
                        dayExercise67,
                        dayExercise68,
                        dayExercise69,
                        dayExercise70,
                        dayExercise71,
                        dayExercise72,
                        dayExercise73,
                        dayExercise74,
                        dayExercise75,
                        dayExercise76,
                        dayExercise77,
                        dayExercise78,
                        dayExercise79,
                        dayExercise80,
                        dayExercise81,
                        dayExercise82,
                        dayExercise83,
                        dayExercise84,
                        dayExercise85,
                        dayExercise86,
                        dayExercise87,
                        dayExercise88,
                        dayExercise89,
                        dayExercise90,
                        dayExercise91,
                        dayExercise92,
                        dayExercise93,
                        dayExercise94,
                        dayExercise95,
                        dayExercise96,
                        dayExercise97,
                        dayExercise98,
                        dayExercise99,
                        dayExercise100,
                        dayExercise101,
                        dayExercise102,
                        dayExercise103,
                        dayExercise104,
                        dayExercise105,
                        dayExercise106,
                        dayExercise107,
                        dayExercise108,
                        dayExercise109,
                        dayExercise110,
                        dayExercise111,
                        dayExercise112,
                        dayExercise113,
                        dayExercise114,
                        dayExercise115,
                        dayExercise116,
                        dayExercise117,
                        dayExercise118,
                        dayExercise119,
                        dayExercise120
                    )
                )
            )

            roomDb.getOneTwentyDaysDao().insertAll(list)
        }
    }
}