package com.bytezaptech.jawlineexercise_faceyoga.ui.details

import android.os.Bundle
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.bytezaptech.jawlineexercise_faceyoga.R
import com.bytezaptech.jawlineexercise_faceyoga.adapters.ViewPagerAdapter
import com.bytezaptech.jawlineexercise_faceyoga.data.local.entities.UserExerciseDetails
import com.bytezaptech.jawlineexercise_faceyoga.data.repositories.AuthRepository
import com.bytezaptech.jawlineexercise_faceyoga.databinding.ActivityOnboardDetailsBinding
import com.bytezaptech.jawlineexercise_faceyoga.utils.Error
import com.bytezaptech.jawlineexercise_faceyoga.utils.MyApplication
import com.bytezaptech.jawlineexercise_faceyoga.utils.Success
import com.bytezaptech.jawlineexercise_faceyoga.utils.setCurrentItem
import com.bytezaptech.jawlineexercise_faceyoga.utils.showError
import javax.inject.Inject

class OnboardDetailsActivity : AppCompatActivity() {
    lateinit var binding: ActivityOnboardDetailsBinding
    lateinit var viewModel: OnBoardDetailsViewModel
    lateinit var adapter: ViewPagerAdapter
    private var userExerciseDetails: UserExerciseDetails? = null

    @Inject
    lateinit var authRepository: AuthRepository
    override fun onCreate(savedInstanceState: Bundle?) {
        (application as MyApplication).appComponent.getAuthSubcomponent().create().inject(this)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = DataBindingUtil.setContentView(this, R.layout.activity_onboard_details)
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.right)
            insets
        }

        viewModel = ViewModelProvider(
            this,
            OnBoardDetailsViewModelFactory(authRepository)
        )[OnBoardDetailsViewModel::class.java]

        val listOfFragment = listOf(
            GenderFragment(),
            NameFragment(),
            AgeFragment(),
            WeightFragment(),
            ScheduleFragment(),
            OnboardSuccessFragment()
        )
        adapter = ViewPagerAdapter(listOfFragment, this)
        binding.viewPager2.adapter = adapter
        binding.viewPager2.isUserInputEnabled = false

        setObservers()
    }

    fun setObservers() {
        viewModel.genderLiveData.observe(this) {
            when (it) {
                is Success<*> -> {
                    userExerciseDetails = UserExerciseDetails()
                    userExerciseDetails?.let { data -> data.gender = it.data.toString() }
                    binding.progressBar.setProgress(15, true)
                    binding.viewPager2.setCurrentItem(
                        1,
                        400,
                        AccelerateDecelerateInterpolator(),
                        binding.viewPager2.width
                    )
                }

                is Error -> {
                    Toast(this).apply {
                        showError(
                            this,
                            this@OnboardDetailsActivity,
                            binding.root as ViewGroup,
                            it.error
                        )
                    }
                }

                else -> {
                }
            }
        }

        viewModel.nameLiveData.observe(this) {
            when (it) {
                is Success<*> -> {
                    userExerciseDetails?.let { data -> data.name = it.data.toString() }
                    binding.progressBar.setProgress(30, true)
                    binding.viewPager2.setCurrentItem(
                        2,
                        400,
                        AccelerateDecelerateInterpolator(),
                        binding.viewPager2.width
                    )
                }

                is Error -> {
                    Toast(this).apply {
                        this.showError(
                            this,
                            this@OnboardDetailsActivity,
                            binding.root as ViewGroup,
                            it.error
                        )
                    }
                }

                else -> {
                }
            }
        }

        viewModel.ageLiveData.observe(this) {
            when (it) {
                is Success<*> -> {
                    userExerciseDetails?.let { data -> data.age = it.data.toString() }
                    binding.progressBar.setProgress(45, true)
                    binding.viewPager2.setCurrentItem(
                        3,
                        400,
                        AccelerateDecelerateInterpolator(),
                        binding.viewPager2.width
                    )
                }

                is Error -> {
                    Toast(this).apply {
                        this.showError(
                            this,
                            this@OnboardDetailsActivity,
                            binding.root as ViewGroup,
                            it.error
                        )
                    }
                }

                else -> {
                }
            }
        }

        viewModel.weightLiveData.observe(this) {
            when (it) {
                is Success<*> -> {
                    userExerciseDetails?.let { data -> data.weight = it.data.toString() }
                    binding.progressBar.setProgress(60, true)
                    binding.viewPager2.setCurrentItem(
                        4,
                        400,
                        AccelerateDecelerateInterpolator(),
                        binding.viewPager2.width
                    )
                }

                is Error -> {
                    Toast(this).apply {
                        this.showError(
                            this,
                            this@OnboardDetailsActivity,
                            binding.root as ViewGroup,
                            it.error
                        )
                    }
                }

                else -> {
                }
            }
        }

        viewModel.timeLiveData.observe(this) {
            when (it) {
                is Success<*> -> {
                    userExerciseDetails?.let { data -> data.exerciseTime = it.data.toString() }
                    binding.progressBar.setProgress(100, true)
                }

                is Error -> {
                    Toast(this).apply {
                        this.showError(
                            this,
                            this@OnboardDetailsActivity,
                            binding.root as ViewGroup,
                            it.error
                        )
                    }
                }

                else -> {
                }
            }
        }
    }

    override fun onBackPressed() {
        val currItem = binding.viewPager2.currentItem
        if (currItem > 0) {
            binding.viewPager2.setCurrentItem(
                currItem - 1,
                300,
                AccelerateDecelerateInterpolator(),
                binding.viewPager2.width
            )
            val progress = binding.progressBar.progress - 25
            binding.progressBar.setProgress(progress, true)
        } else super.onBackPressed()
    }
}