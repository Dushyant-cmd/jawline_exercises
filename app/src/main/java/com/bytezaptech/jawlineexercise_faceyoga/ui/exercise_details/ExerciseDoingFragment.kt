package com.bytezaptech.jawlineexercise_faceyoga.ui.exercise_details

import android.content.Context
import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bytezaptech.jawlineexercise_faceyoga.R
import com.bytezaptech.jawlineexercise_faceyoga.data.repositories.MainRepository
import com.bytezaptech.jawlineexercise_faceyoga.databinding.FragmentExerciseDoingBinding
import com.bytezaptech.jawlineexercise_faceyoga.ui.home.HomeViewModel
import com.bytezaptech.jawlineexercise_faceyoga.ui.home.HomeViewModelFactory
import com.bytezaptech.jawlineexercise_faceyoga.ui.main.MainActivity
import com.bytezaptech.jawlineexercise_faceyoga.utils.MyApplication
import com.bytezaptech.jawlineexercise_faceyoga.utils.Success
import com.bytezaptech.jawlineexercise_faceyoga.utils.showSuccess
import javax.inject.Inject

class ExerciseDoingFragment : Fragment() {
    private lateinit var binding: FragmentExerciseDoingBinding
    private lateinit var viewModel: HomeViewModel
    private var countDown: CountDownTimer? = null
    private var currEx: Int = 0

    @Inject
    lateinit var mainRepo: MainRepository
    val args: ExerciseDoingFragmentArgs by navArgs()//by lazy operator which assign args variable when it comes in use.

    override fun onAttach(context: Context) {
        super.onAttach(context)
        ((activity as MainActivity).application as MyApplication).appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_exercise_doing, container, false)
        viewModel =
            ViewModelProvider(this, HomeViewModelFactory(mainRepo))[HomeViewModel::class.java]

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        viewModel.nextExerciseDoing()
        setObservers()
        setListeners()
        return binding.root
    }

    private fun setObservers() {
        viewModel.exerciseDoingLiveData.observe(viewLifecycleOwner) {
            when (it) {
                is Success<*> -> {
                    binding.cvPrev.visibility = View.VISIBLE
                    binding.cvNext.visibility = View.VISIBLE
                    binding.finishBtn.visibility = View.GONE

                    // hold current exercise
                    currEx = it.data as Int + 1

                    if (it.data == 0) binding.cvPrev.visibility = View.INVISIBLE
                    else if (it.data == (args.data.size - 1)) {
                        binding.cvPrev.visibility = View.GONE
                        binding.cvNext.visibility = View.GONE
                        binding.controlLy.visibility = View.GONE
                        binding.finishBtn.visibility = View.VISIBLE
                    }

                    binding.totalExTv.text = "${it.data + 1} / ${args.data.size}"

                    val exercise = args.data[it.data]
                    binding.exerciseLv.setAnimation(exercise.img!!)
                    binding.titleTv.text = exercise.title
                    binding.tvSec.text = exercise.duration

                    binding.pb.max = exercise.duration?.toInt() ?: 0
                    val durMillis = exercise.duration?.toLong()?.times(1000) ?: 0
                    startTimer((durMillis))
                }

                else -> {}
            }
        }
    }

    private fun startTimer(duration: Long) {
        countDown?.cancel()
        countDown = object : CountDownTimer(duration, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val sec = ((millisUntilFinished / 1000).toInt())
                binding.pb.setProgress(sec, true)
                binding.tvSec.text = if (sec < 10) "0$sec" else sec.toString()
            }

            override fun onFinish() {
                //Rest Fragment
                activity?.let {
                    if (currEx == (args.data.size - 1)) {
                        completedExercise()
                    } else {
                        val action = ExerciseDoingFragmentDirections.exerciseDoingToWaitFragment(args.data, args.exerciseChallenge, currEx)
                        findNavController().navigate(action)
                    }
                }
            }
        }

        countDown?.start()
    }

    private fun setListeners() {
        binding.finishBtn.setOnClickListener {
            completedExercise()
        }

        binding.playIv.setOnClickListener {
            binding.pauseIv.visibility = View.VISIBLE
            binding.playIv.visibility = View.GONE

            binding.exerciseLv.playAnimation()
            val durMillis = binding.tvSec.text.toString().toLong().times(1000)
            startTimer(durMillis)
        }

        binding.pauseIv.setOnClickListener {
            binding.pauseIv.visibility = View.GONE
            binding.playIv.visibility = View.VISIBLE

            binding.exerciseLv.pauseAnimation()
            countDown?.cancel()
        }

        binding.ivUp.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.cvNext.setOnClickListener {
            val action = ExerciseDoingFragmentDirections.exerciseDoingToWaitFragment(args.data, args.exerciseChallenge, currEx)
            findNavController().navigate(action)
            countDown?.cancel()
        }

        binding.cvPrev.setOnClickListener {
            viewModel.prevExerciseDoing()
        }
    }

    private fun completedExercise() {
        val growthImg = ""
        //Check if day is divided by 6 then ask for face photo of user to track growth.
        when (args.exerciseChallenge.daysCompleted?.rem(6)) {
            0 -> {
            }

            else -> {}
        }.apply {
            viewModel.completeDayExercise(args.exerciseChallenge, growthImg)
            Toast(requireContext()).apply {
                showSuccess(
                    this,
                    requireContext(),
                    binding.root as ViewGroup,
                    "Day ${args.exerciseChallenge.daysCompleted} Finished"
                )

                findNavController().navigate(ExerciseDoingFragmentDirections.exerciseDoingToHomeFragment())
            }
        }
    }
}