package com.bytezaptech.jawlineexercise_faceyoga.data.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bytezaptech.jawlineexercise_faceyoga.R
import com.bytezaptech.jawlineexercise_faceyoga.data.local.RoomDb
import com.bytezaptech.jawlineexercise_faceyoga.data.local.SharedPref
import com.bytezaptech.jawlineexercise_faceyoga.data.local.entities.ExerciseChallenge
import com.bytezaptech.jawlineexercise_faceyoga.data.local.entities.GrowthEntity
import com.bytezaptech.jawlineexercise_faceyoga.data.local.entities.OneTwentyDaysExerciseEntity
import com.bytezaptech.jawlineexercise_faceyoga.data.local.entities.SixtyDaysExerciseEntity
import com.bytezaptech.jawlineexercise_faceyoga.data.local.entities.ThirtyDaysExerciseEntity
import com.bytezaptech.jawlineexercise_faceyoga.models.EachDayExerciseModel
import com.bytezaptech.jawlineexercise_faceyoga.utils.Constants
import com.bytezaptech.jawlineexercise_faceyoga.utils.ExerciseResponse
import com.bytezaptech.jawlineexercise_faceyoga.utils.ExerciseSuccess
import com.bytezaptech.jawlineexercise_faceyoga.utils.Response
import com.bytezaptech.jawlineexercise_faceyoga.utils.Success
import java.text.SimpleDateFormat
import java.util.Locale
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

    private var userProfileData: MutableLiveData<Response> = MutableLiveData()
    val userProfile: LiveData<Response>
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

    private val eachDayDetMut: MutableLiveData<Response> = MutableLiveData()

    val eachDayDetails: LiveData<Response>
        get() {
            return eachDayDetMut
        }

    fun isUserLoggedIn() {
        sharedPref.getBoolean(Constants.KEY_IS_USER_LOGGED).apply {
            splashAuthLiveDataMut.value = Success(this)
        }
    }

    fun getUserDetails() {
        userProfileData.value = Success(roomDb.getUserDao().getUser())
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
            ExerciseSuccess(roomDb.getThirtyDaysDao().getExerciseByDay(day), day)
    }

    fun getSixtyDayExercise(day: String) {
        exerciseDay.value =
            ExerciseSuccess(roomDb.getSixtyDaysDao().getExerciseByDay(day), day)
    }

    fun getOneTwentyDayExercise(day: String) {
        exerciseDay.value =
            ExerciseSuccess(roomDb.getOneTwentyDaysDao().getExerciseByDay(day), day)
    }

    fun addAllExerciseDays() {
        val upsideDownNods by lazy {
            EachDayExerciseModel(
                "1",
                R.raw.a_one,
                "UP & DOWN NODS",
                "45",
                "Keep Your back straight and look straight ahead\n\nMove your head up and down slowly to feel your muscle stretching."
            )
        }
        val chinTucks by lazy {
            EachDayExerciseModel(
                "2",
                R.raw.a_two,
                "CHIN TUCKS",
                "45",
                "Sit in a comfortable position. Begin by tilting your head forward, so that your chin is sticking out in front of your chest\n\n Slowly draw your chin back and down, as if you are trying to touch the back of your neck with your chin.\n\nHold the position for 10 seconds, then release back to the starting position. Repeat 5 times"
            )
        }
        val upwardChew by lazy {
            EachDayExerciseModel(
                "3",
                R.raw.a_one,
                "UPWARD CHEWING",
                "45",
                "keep your shoulders and back straight\n\nTilt your head back and look up at the ceiling Open nd close your jaw (like chewing a gum) and feel the skin around your neck and chin tighten.\n\nHold your jaw while looking at the ceiling for 1 second and release. Keep doing the same procedure for 30 seconds."
            )
        }
        val extendTongue by lazy {
            EachDayExerciseModel(
                "4",
                R.raw.a_one,
                "EXTEND YOUR TONGUE",
                "45",
                "Keep your back straight and look straight ahead\n\nopen your mouth\n\nstick out your tongue as fas as you can.\n\n\nHold for 10 seconds then repeat 3 times"
            )
        }
        val openMouthWidely by lazy {
            EachDayExerciseModel(
                "4",
                R.raw.a_one,
                "OPEN MOUTH WIDELY",
                "45",
                "Open your mouth, stretch to open it wide\n\nSeparate your teeth from the upper to lower jaw as much as your can\n\nSpread the mouth as much as you can\n\n\nHold for 3 seconds. Slowly return your jaw, lips, and then teeth back to their normal position and repeat for 30 seconds"
            )
        }
        val massageFace by lazy {
            EachDayExerciseModel(
                "5",
                R.raw.a_one,
                "MASSAGE YOUR FACE",
                "45",
                "Use your index and middle finger\n\nMassage your whole face (forehead, chin, cheeks, eye brows, etc.).\n\n\nDo it for 30 seconds, then relax."
            )
        }
        val mouthWash by lazy {
            EachDayExerciseModel(
                "6",
                R.raw.a_one,
                "MOUTH WASH EXERCISE",
                "45",
                "This is a exercise"
            )
        }
        val tiltHeadLeftRight by lazy {
            EachDayExerciseModel(
                "8",
                R.raw.a_one,
                "TILT YOUR HEAD LEFT & RIGHT",
                "45",
                "This is a exercise"
            )
        }
        val pushTongueOut by lazy {
            EachDayExerciseModel(
                "8",
                R.raw.a_one,
                "PUSHING THE TONGUE OUTWARD",
                "45",
                "This is a exercise"
            )
        }

        val listOfExercises = listOf(
            upsideDownNods,
            chinTucks,
            upwardChew,
            extendTongue,
            openMouthWidely,
            massageFace,
            mouthWash,
            tiltHeadLeftRight,
            pushTongueOut
        )

        if (roomDb.getThirtyDaysDao().getExercises().isEmpty()) {

            val list = ArrayList<ThirtyDaysExerciseEntity>()
            var j = 0
            for (i in 0..30) {
                val exercises = ArrayList<EachDayExerciseModel>()

                ThirtyDaysExerciseEntity(
                    null,
                    "${i + 1}",
                    0,
                    listOf(
                        listOfExercises[i],
                        listOfExercises[i],
                        listOfExercises[i],
                        listOfExercises[i],
                        listOfExercises[i],
                        listOfExercises[i]
                    )
                )

                j++
                if(j == 5)
                    j = 0
            }

            roomDb.getThirtyDaysDao().deleteAll()
            roomDb.getThirtyDaysDao().insertAll(list)
        }

        if (roomDb.getSixtyDaysDao().getExercises().isEmpty()) {

            val list = listOf(
                SixtyDaysExerciseEntity(
                    0,
                    "1",
                    0,
                    listOf(
                        upsideDownNods,
                        chinTucks,
                        upwardChew,
                        extendTongue,
                        openMouthWidely,
                        massageFace
                    )
                ),
                SixtyDaysExerciseEntity(
                    0,
                    "2",
                    0,
                    listOf(
                        upsideDownNods,
                        chinTucks,
                        upwardChew,
                        extendTongue,
                        openMouthWidely,
                        massageFace
                    )
                )
            )

            roomDb.getSixtyDaysDao().deleteAll()
            roomDb.getSixtyDaysDao().insertAll(list)
        }

        if (roomDb.getOneTwentyDaysDao().getDetails().isEmpty()) {

            val list = listOf(
                OneTwentyDaysExerciseEntity(
                    0, "1",
                    0,
                    listOf(
                        upsideDownNods,
                        chinTucks,
                        upwardChew,
                        extendTongue,
                        openMouthWidely,
                        massageFace
                    )
                ),
                OneTwentyDaysExerciseEntity(
                    0, "2",
                    0,
                    listOf(
                        upsideDownNods,
                        chinTucks,
                        upwardChew,
                        extendTongue,
                        openMouthWidely,
                        massageFace
                    )
                )
            )

            roomDb.getOneTwentyDaysDao().deleteAll()
            roomDb.getOneTwentyDaysDao().insertAll(list)
        }
    }

    fun displayEachDayDetails(dayDetails: EachDayExerciseModel) {
        eachDayDetMut.value = Success(dayDetails)
    }

    suspend fun completeDayExercise(exerciseChallenge: ExerciseChallenge, growthImg: String) {
        //Insert growth
        val spf = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
        val date = spf.format(System.currentTimeMillis())
        val currGrowthEntity =
            roomDb.getGrowthDao().getGrowthById(exerciseChallenge.daysCompleted!!, date)
        if (currGrowthEntity == null) {
            val growthEntity = GrowthEntity(
                null,
                exerciseChallenge.name,
                exerciseChallenge.daysCompleted,
                date,
                growthImg
            )
            roomDb.getGrowthDao().insert(growthEntity)
        }

        if (exerciseChallenge.daysCompleted != exerciseChallenge.totalDays) {
            val exerciseChallenge2 = ExerciseChallenge(
                exerciseChallenge.id,
                exerciseChallenge.name,
                exerciseChallenge.daysCompleted + 1,
                exerciseChallenge.totalDays,
                exerciseChallenge.isFinished
            )
            roomDb.getExerciseChallengeDao().update(exerciseChallenge2)
        }

        //Increment completed day
    }
}