package com.bytezaptech.jawlineexercise_faceyoga.ui.exercise_details

import android.animation.TypeEvaluator
import android.animation.ValueAnimator
import android.content.Context
import android.os.Bundle
import android.os.CountDownTimer
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bytezaptech.jawlineexercise_faceyoga.R
import com.bytezaptech.jawlineexercise_faceyoga.data.local.entities.ExerciseChallenge
import com.bytezaptech.jawlineexercise_faceyoga.data.repositories.MainRepository
import com.bytezaptech.jawlineexercise_faceyoga.databinding.FragmentCountDownAnimationBinding
import com.bytezaptech.jawlineexercise_faceyoga.models.EachDayExerciseModel
import com.bytezaptech.jawlineexercise_faceyoga.ui.home.HomeViewModel
import com.bytezaptech.jawlineexercise_faceyoga.ui.home.HomeViewModelFactory
import com.bytezaptech.jawlineexercise_faceyoga.ui.main.MainActivity
import com.bytezaptech.jawlineexercise_faceyoga.utils.MyApplication
import com.google.android.material.animation.AnimationUtils
import kotlinx.coroutines.NonCancellable.start
import javax.inject.Inject

class CountDownAnimationFragment : Fragment() {
    private lateinit var binding: FragmentCountDownAnimationBinding
    private lateinit var exerciseChallenge: ExerciseChallenge
    private lateinit var allExercise: Array<EachDayExerciseModel>
    val args: CountDownAnimationFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_count_down_animation, container, false)

        exerciseChallenge = args.exerciseChallenge
        allExercise = args.data
        startAnim()
        return binding.root
    }

    private fun startAnim() {
        var sec = 4
        val countDownTimer = object: CountDownTimer(4000, 1500) {
            override fun onTick(millisInFuture: Long) {
                sec--
                binding.tvSec.text = (sec).toString()
                binding.tvSec.startAnimation(android.view.animation.AnimationUtils.loadAnimation(requireContext(), R.anim.zoom_out))
            }

            override fun onFinish() {
                //Move user to exercise doing fragment
                val action = CountDownAnimationFragmentDirections.countDownAnimationFragmentToExerciseDoing(allExercise, exerciseChallenge)
                findNavController().navigate(action)
            }
        }
        countDownTimer.start()

        binding.dayTv.text = "Day ${exerciseChallenge.daysCompleted}"
        binding.dayLy.startAnimation(android.view.animation.AnimationUtils.loadAnimation(requireContext(), R.anim.zoom_out))
    }
}