package com.bytezaptech.jawlineexercise_faceyoga.ui.exercise_details

import android.content.Context
import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bytezaptech.jawlineexercise_faceyoga.R
import com.bytezaptech.jawlineexercise_faceyoga.data.repositories.MainRepository
import com.bytezaptech.jawlineexercise_faceyoga.databinding.FragmentExerciseWaitBinding
import com.bytezaptech.jawlineexercise_faceyoga.ui.home.HomeViewModel
import com.bytezaptech.jawlineexercise_faceyoga.ui.home.HomeViewModelFactory
import com.bytezaptech.jawlineexercise_faceyoga.utils.MyApplication
import com.bytezaptech.jawlineexercise_faceyoga.utils.Success
import javax.inject.Inject

class ExerciseWaitFragment : Fragment() {
    private lateinit var binding: FragmentExerciseWaitBinding
    private lateinit var viewModel: HomeViewModel

    @Inject
    lateinit var mainRepository: MainRepository
    private val args: ExerciseWaitFragmentArgs by navArgs<ExerciseWaitFragmentArgs>()
    private var countTimer: CountDownTimer? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as MyApplication).appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_exercise_wait, container, false)
        viewModel =
            ViewModelProvider(this, HomeViewModelFactory(mainRepository))[HomeViewModel::class.java]

        setupViews()
        setListeners()
        return binding.root
    }

    private fun setupViews() {
        val exerciseDet = args.data[args.currentExercise]
        val currEx = args.currentExercise + 1
        binding.totalExTv.text = "${currEx} / ${args.data.size}"
        binding.lottie.setAnimation(exerciseDet.img!!)
        startTimer(args.exerciseChallenge.waitDur!!)
    }

    private fun startTimer(duration: Long) {
        countTimer?.cancel()
        countTimer = object : CountDownTimer(duration, 1000) {
            override fun onTick(p0: Long) {
                var count = (p0 / 1000).toString()
                if(count.length == 1)
                    count = "0$count"

                binding.secTv.text = count

            }

            override fun onFinish() {
                activity?.let {
                    findNavController().popBackStack()
                }
            }
        }

        countTimer?.start()
    }

    private fun setListeners() {
        binding.addCountTv.setOnClickListener {
            val nextDur = binding.secTv.text.toString().toLong().times(1000) + 20000
            startTimer(nextDur)
        }

        binding.skipBtn.setOnClickListener {
            findNavController().popBackStack()
        }
    }
}