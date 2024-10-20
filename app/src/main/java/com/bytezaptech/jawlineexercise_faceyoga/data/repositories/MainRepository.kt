package com.bytezaptech.jawlineexercise_faceyoga.data.repositories

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bytezaptech.jawlineexercise_faceyoga.R
import com.bytezaptech.jawlineexercise_faceyoga.data.local.RoomDb
import com.bytezaptech.jawlineexercise_faceyoga.data.local.SharedPref
import com.bytezaptech.jawlineexercise_faceyoga.data.local.entities.ArticleEntity
import com.bytezaptech.jawlineexercise_faceyoga.data.local.entities.ExerciseChallenge
import com.bytezaptech.jawlineexercise_faceyoga.data.local.entities.GrowthEntity
import com.bytezaptech.jawlineexercise_faceyoga.data.local.entities.LanguageEntity
import com.bytezaptech.jawlineexercise_faceyoga.data.local.entities.OneTwentyDaysExerciseEntity
import com.bytezaptech.jawlineexercise_faceyoga.data.local.entities.SixtyDaysExerciseEntity
import com.bytezaptech.jawlineexercise_faceyoga.data.local.entities.ThirtyDaysExerciseEntity
import com.bytezaptech.jawlineexercise_faceyoga.data.local.entities.UserEntity
import com.bytezaptech.jawlineexercise_faceyoga.models.EachDayExerciseModel
import com.bytezaptech.jawlineexercise_faceyoga.models.ExerciseListModel
import com.bytezaptech.jawlineexercise_faceyoga.ui.home.ThirtyDaysFragment
import com.bytezaptech.jawlineexercise_faceyoga.utils.Constants
import com.bytezaptech.jawlineexercise_faceyoga.utils.Error
import com.bytezaptech.jawlineexercise_faceyoga.utils.ExerciseResponse
import com.bytezaptech.jawlineexercise_faceyoga.utils.ExerciseSuccess
import com.bytezaptech.jawlineexercise_faceyoga.utils.Response
import com.bytezaptech.jawlineexercise_faceyoga.utils.Success
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Locale
import javax.inject.Inject


class MainRepository @Inject constructor(
    private val roomDb: RoomDb,
    private val sharedPref: SharedPref,
    val firebaseAuth: FirebaseAuth,
    val firestore: FirebaseFirestore,
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

    private val isExerciseMut: MutableLiveData<Response> = MutableLiveData()

    val isExerciseLiveData: LiveData<Response>
        get() {
            return isExerciseMut
        }

    private val growthListMLD: MutableLiveData<Response> = MutableLiveData()

    val growthListLD: LiveData<Response>
        get() {
            return growthListMLD
        }

    private val growthListAllMLD: MutableLiveData<Response> = MutableLiveData()

    val growthListAllLD: LiveData<Response>
        get() {
            return growthListAllMLD
        }

    private var articleMLD: MutableLiveData<Response> = MutableLiveData()

    val articleLD: LiveData<Response>
        get() {
            return articleMLD
        }

    private var historyMLD: MutableLiveData<Response> = MutableLiveData()

    val historyLD: LiveData<Response>
        get() {
            return historyMLD
        }

    private var signOutMLD: MutableLiveData<Response> = MutableLiveData()

    val signOutLD: LiveData<Response>
        get() {
            return signOutMLD
        }

    private var languageMLD: MutableLiveData<Response> = MutableLiveData()

    val languageLD: LiveData<Response>
        get() {
            return languageMLD
        }

    private var languageSelMLD: MutableLiveData<Response> = MutableLiveData()

    val languageSelLD: LiveData<Response>
        get() {
            return languageSelMLD
        }

    private var restDurMLD: MutableLiveData<Response> = MutableLiveData()

    val restDurLD: LiveData<Response>
        get() {
            return restDurMLD
        }

    private var updateMLD: MutableLiveData<Response> = MutableLiveData()

    val updateLD: LiveData<Response>
        get() {
            return updateMLD
        }

    fun getGrowthListWithImage() {
        growthListMLD.value = Success(roomDb.getGrowthDao().getGrowthListWithImage())
    }

    fun getGrowthListWithoutImage() {
        val list = roomDb.getGrowthDao().getGrowthList()
        val value = if(list.isNotEmpty()) Success(list[list.lastIndex]) else Error("No data")
        growthListAllMLD.value = value
    }

    fun getHistory() {
        val list = roomDb.getGrowthDao().getGrowthList()
        val value = if(list.isNotEmpty()) Success(list) else Error("No data")
        historyMLD.value = value
    }

    fun isUserLoggedIn() {
        sharedPref.getBoolean(Constants.KEY_IS_USER_LOGGED).apply {
            splashAuthLiveDataMut.value = Success(this)
        }
    }

    fun getUserDetails() {
        userProfileData.value = Success(roomDb.getUserDao().getUser())
    }

    suspend fun addExerciseChallenges() {
        if (roomDb.getExerciseChallengeDao().getAll().isEmpty()) {
            val thirtyDays = ExerciseChallenge(0, "30 Days", 1, 30, false, 20000)
            val sixtyDays = ExerciseChallenge(1, "60 Days", 1, 60, false, 20000)
            val oneTwentyDays = ExerciseChallenge(2, "120 Days", 1, 120, false, 20000)

            roomDb.getExerciseChallengeDao().insert(thirtyDays)
            roomDb.getExerciseChallengeDao().insert(sixtyDays)
            roomDb.getExerciseChallengeDao().insert(oneTwentyDays)
        }

        val listOfExercises = roomDb.getExerciseChallengeDao().getAll()
        val list = ArrayList<Fragment>()
        for (i in listOfExercises.indices) {
            val listOfDays = ArrayList<ExerciseListModel>()

            for (j in 1..listOfExercises[i].totalDays!!) {
                var isFinished = false
                if (j <= listOfExercises[i].daysCompleted!!)
                    isFinished = true

                listOfDays.add(ExerciseListModel("Day $j", j, isFinished, listOfExercises[i]))
            }

            val bundle = Bundle()
            bundle.putSerializable("list", listOfDays)

            when (listOfExercises[i].totalDays) {
                30 -> {
                    val frag = ThirtyDaysFragment()
                    frag.arguments = bundle
                    list.add(frag)
                }

//                            60 -> {
//                                val frag = SixtyDaysFragment()
//                                frag.arguments = bundle
//                                list.add(frag)
//                            }
//
//                            120 -> {
//                                val frag = OneTwentyDaysFragment()
//                                frag.arguments = bundle
//                                list.add(frag)
//                            }
            }
        }

        withContext(Dispatchers.Main) {
            exerciseChallengeMut.value = Success(list)
        }
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
                R.raw.a_five,
                "UP & DOWN NODS",
                "45",
                "Keep Your back straight and look straight ahead\n\nMove your head up and down slowly to feel your muscle stretching."
            )
        }
        val chinTucks by lazy {
            EachDayExerciseModel(
                "2",
                R.raw.a_eight,
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
                R.raw.a_two,
                "EXTEND YOUR TONGUE",
                "45",
                "Keep your back straight and look straight ahead\n\nopen your mouth\n\nstick out your tongue as fas as you can.\n\n\nHold for 10 seconds then repeat 3 times"
            )
        }
        val openMouthWidely by lazy {
            EachDayExerciseModel(
                "5",
                R.raw.a_three,
                "OPEN MOUTH WIDELY",
                "45",
                "Open your mouth, stretch to open it wide\n\nSeparate your teeth from the upper to lower jaw as much as your can\n\nSpread the mouth as much as you can\n\n\nHold for 3 seconds. Slowly return your jaw, lips, and then teeth back to their normal position and repeat for 30 seconds"
            )
        }
        val massageFace by lazy {
            EachDayExerciseModel(
                "6",
                R.raw.a_nine,
                "MASSAGE YOUR FACE",
                "45",
                "Use your index and middle finger\n\nMassage your whole face (forehead, chin, cheeks, eye brows, etc.).\n\n\nDo it for 30 seconds, then relax."
            )
        }
        val mouthWash by lazy {
            EachDayExerciseModel(
                "7",
                R.raw.a_four,
                "MOUTH WASH EXERCISE",
                "45",
                "This is a exercise"
            )
        }
        val tiltHeadLeftRight by lazy {
            EachDayExerciseModel(
                "8",
                R.raw.a_six,
                "TILT YOUR HEAD LEFT & RIGHT",
                "45",
                "Keep your back and shoulders straight\nRotate your head to the RIGHT side, pull out your tongue as far as you can and keep it out for 3 seconds.\nRotate your head to the LEFT side, pull out your tongue as far as you can and keep it out for 3 seconds.\n\n\nContinue the exercise for 30 seconds."
            )
        }
        val pushTongueOut by lazy {
            EachDayExerciseModel(
                "9",
                R.raw.a_seven,
                "PUSHING THE TONGUE OUTWARD",
                "45",
                "Keep your back straight and look straight ahead\n\nTilt your head for left to right for continuously for 30 seconds"
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
            var slot = 1
            for (i in 0 until 30) {
                val exList = ArrayList<EachDayExerciseModel>()
                var exLimit = 0
                when (slot) {
                    1 -> {
                        exLimit = 5
                    }

                    2 -> {
                        exLimit = 6
                    }

                    3 -> {
                        exLimit = 7
                    }

                    4 -> {
                        exLimit = 8
                    }

                    5 -> {
                        exLimit = 9
                    }
                }

                var k = 0
                while (k < exLimit) {
                    exList.add(listOfExercises[k])

                    if (k == exLimit.dec()) when (slot) {
                        1 -> {
                            slot = 2
                        }

                        2 -> {
                            slot = 3
                        }

                        3 -> {
                            slot = 4
                        }

                        4 -> {
                            slot = 5
                        }

                        5 -> {
                            slot = 1
                        }
                    }

                    k++
                }

                list.add(
                    ThirtyDaysExerciseEntity(
                        null,
                        "${i + 1}",
                        0,
                        exList
                    )
                )
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
        var date = spf.format(System.currentTimeMillis())
        val currGrowthEntity =
            roomDb.getGrowthDao().getGrowthById(exerciseChallenge.daysCompleted!!, date)
        if (currGrowthEntity == null) {
            val growthEntity = GrowthEntity(
                null,
                exerciseChallenge.name,
                exerciseChallenge.daysCompleted,
                date,
                System.currentTimeMillis(),
                growthImg,
                exerciseChallenge.totalDays
            )
            roomDb.getGrowthDao().insert(growthEntity)
        }

        var dayCompleted = exerciseChallenge.daysCompleted
        if (exerciseChallenge.daysCompleted != exerciseChallenge.totalDays)
            dayCompleted = exerciseChallenge.daysCompleted + 1

        val exerciseChallenge2 = ExerciseChallenge(
            exerciseChallenge.id,
            exerciseChallenge.name,
            dayCompleted,
            exerciseChallenge.totalDays,
            exerciseChallenge.isFinished,
            exerciseChallenge.waitDur
        )
        roomDb.getExerciseChallengeDao().update(exerciseChallenge2)
    }

    suspend fun isExerciseCompleted(exerciseChallenge: ExerciseChallenge) {
        val spf = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
        val date = spf.format(System.currentTimeMillis())
        val currGrowthEntity = roomDb.getGrowthDao().getGrowthById(exerciseChallenge.daysCompleted!!, date)
        isExerciseMut.value = Success(currGrowthEntity)
    }

    suspend fun addArticles() {
        if(roomDb.getArticleDao().getAll().isEmpty()) {
            val list = listOf(ArticleEntity(null, "Neck curlup", "Think of this as an abdominal curl for your neck. It’s done lying on your back with the tongue pressed on the roof of the mouth. This activates the front neck muscles.\n" +
                    "\n" +
                    "1. Bring your chin to your chest and then lift your head off of the ground about 2 inches. Don’t lift your stomach and don’t poke your chin out.\n" +
                    "2. Start by doing 3 sets for 10 repetitions and gradually build up to more.\n" +
                    "3. Take your time because these muscles are often underdeveloped and can cause neck strain if you try too much too fast.", R.drawable.neck_curlup),
                ArticleEntity(null, "Collar bone backup", "This can be done seated, standing, or lying down on your back.\n" +
                        "\n" +
                        "Keeping your head level with the floor, bring your head back several inches to feel muscles on either side of your throat contract and relax.\n" +
                        "Start with 3 sets of 10 repetitions at first, and then progress to holding the position for more than 30 seconds.\n" +
                        "Make sure that your ears stay over your shoulders and your head stays level.", R.drawable.collar_bone_backup),
                ArticleEntity(null, "Tongue twister", "This exercise will target the muscles underneath the chin.\n" +
                        "\n" +
                        "Place your tongue on the roof of your mouth directly behind your teeth.\n" +
                        "Press your tongue to completely close the roof of your mouth and add tension.\n" +
                        "Begin humming and making a vibrating sound. This will activate the muscles.\n" +
                        "Complete 3 sets of 15.", R.drawable.tounge_twister),
                ArticleEntity(null, "Vowel Sounds", "These movements target the muscles around the mouth and on the sides of the lips.\n" +
                        "\n" +
                        "Open your mouth wide then say “O,” followed by “E.”\n" +
                        "Be sure to exaggerate these sounds and movements and not show or touch your teeth.\n" +
                        "Perform 3 sets of 15.", R.drawable.vowel_sounds),
                ArticleEntity(null, "Chinup", "This exercise helps lift the face and chin muscles.\n" +
                        "\n" +
                        "With your mouth closed, push your lower jaw out and lift your lower lip.\n" +
                        "You should feel a stretch build just under the chin and in the jawline.\n" +
                        "Hold the position for 10–15 seconds, then relax.\n" +
                        "Perform 3 sets of 15.", R.drawable.chinup))

            roomDb.getArticleDao().insertAll(list)
        }
    }

    fun getArticles() {
        articleMLD.value = Success(roomDb.getArticleDao().getAll())
    }

    fun signOutUser() {
        roomDb.clearAllTables()
        sharedPref.clear()
        signOutMLD.value = Success("Sign-out successfully")
    }

    fun deleteAccount() {
        val docEmId = sharedPref.getString(Constants.EMAIL) ?: ""
        firestore.collection("users").document(docEmId)
            .delete().addOnCompleteListener {
                if (it.isSuccessful) {
                    roomDb.clearAllTables()
                    sharedPref.clear()
                    firebaseAuth.signOut()
                    signOutMLD.value = Success("Account deleted successfully")
                }
            }
    }

    fun getLanguages() {
        if(roomDb.getLanguageDao().getAll().isEmpty()) {
            val list = listOf(LanguageEntity(null, "English", "en", false), LanguageEntity(null,"Hindi", "hi", false))
            for(i in list.indices) {
                if(list[i].langCode == sharedPref.getString(Constants.LANGUAGE_SELECTED))
                    list[i].isSelected = true
            }
            roomDb.getLanguageDao().insertAll(list)
        }

        languageMLD.value = Success(roomDb.getLanguageDao().getAll())
    }

    fun setLanguage(languageEntity: LanguageEntity) {
        sharedPref.setString(Constants.LANGUAGE_SELECTED, languageEntity.langCode)
        languageSelMLD.value = Success(languageEntity)
    }

    suspend fun changeRestDuration(duration: Long) {
        val thirtyDays = ExerciseChallenge(0, "30 Days", 1, 30, false, duration)
        val sixtyDays = ExerciseChallenge(1, "60 Days", 1, 60, false, duration)
        val oneTwentyDays = ExerciseChallenge(2, "120 Days", 1, 120, false, duration)

        roomDb.getExerciseChallengeDao().update(thirtyDays)
        roomDb.getExerciseChallengeDao().update(sixtyDays)
        roomDb.getExerciseChallengeDao().update(oneTwentyDays)

        withContext(Dispatchers.IO) {
            restDurMLD.postValue(Success("Rest duration changed successfully"))
        }
    }

    fun getConstants() {
        firestore.collection(Constants.COL_CONSTANTS).document(Constants.DOC_CONSTANTS)
            .get().addOnCompleteListener {
                when(it.isSuccessful) {
                    true -> {
                        val doc = it.result
                        val privacyUrl = doc.getString("privacy_url")
                        val termsUrl = doc.getString("terms_url")
                        val upi = doc.getString("upi")
                        val updateInfo = doc.getString("update_info")
                        val isShowAds = doc.getBoolean("is_show_ads")

                        sharedPref.setString(Constants.PRIVACY_POLICY_URL, privacyUrl ?: "")
                        sharedPref.setString(Constants.TERMS_CONDITIONS_URL, termsUrl ?: "")
                        sharedPref.setString(Constants.UPI, upi ?: "")
                        sharedPref.setString(Constants.UPDATE_INFO, updateInfo ?: "0")
                        sharedPref.setBoolean(Constants.IS_SHOW_ADS, isShowAds ?: false)

                        updateMLD.value = Success(updateInfo)
                    }

                    else -> {}
                }
            }
    }
}