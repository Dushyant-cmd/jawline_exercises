package com.bytezaptech.jawlineexercise_faceyoga.ui.exercise_details

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.bytezaptech.jawlineexercise_faceyoga.adapters.EachDayExerciseAdapter
import com.bytezaptech.jawlineexercise_faceyoga.data.local.entities.ExerciseChallenge
import com.bytezaptech.jawlineexercise_faceyoga.data.local.entities.OneTwentyDaysExerciseEntity
import com.bytezaptech.jawlineexercise_faceyoga.data.local.entities.SixtyDaysExerciseEntity
import com.bytezaptech.jawlineexercise_faceyoga.data.local.entities.ThirtyDaysExerciseEntity
import com.bytezaptech.jawlineexercise_faceyoga.data.repositories.MainRepository
import com.bytezaptech.jawlineexercise_faceyoga.databinding.FragmentExerciseDetailsBinding
import com.bytezaptech.jawlineexercise_faceyoga.models.EachDayExerciseModel
import com.bytezaptech.jawlineexercise_faceyoga.ui.home.HomeViewModel
import com.bytezaptech.jawlineexercise_faceyoga.ui.home.HomeViewModelFactory
import com.bytezaptech.jawlineexercise_faceyoga.ui.main.MainActivity
import com.bytezaptech.jawlineexercise_faceyoga.utils.ExerciseError
import com.bytezaptech.jawlineexercise_faceyoga.utils.ExerciseSuccess
import com.bytezaptech.jawlineexercise_faceyoga.utils.MyApplication
import com.bytezaptech.jawlineexercise_faceyoga.utils.Success
import com.bytezaptech.jawlineexercise_faceyoga.utils.showError
import javax.inject.Inject

class ExerciseDetailsFragment : Fragment() {
    private lateinit var binding: FragmentExerciseDetailsBinding
    private lateinit var viewModel: HomeViewModel
    private lateinit var exerciseChallenge: ExerciseChallenge

    @Inject
    lateinit var mainRepository: MainRepository

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as MyApplication).appComponent.inject(this)
        (requireActivity() as MainActivity).binding.bottomNavViewCv.visibility = View.GONE
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentExerciseDetailsBinding.inflate(inflater, container, false)
        ViewCompat.setOnApplyWindowInsetsListener(binding.root)  { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        viewModel =
            ViewModelProvider(this, HomeViewModelFactory(mainRepository))[HomeViewModel::class.java]
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        setupViews()
        setObservers()
        setListeners()
        return binding.root
    }

    private fun setupViews() {
        val day = arguments?.getInt("day")
        exerciseChallenge = arguments?.getSerializable("exerciseChallenge") as ExerciseChallenge

        when(exerciseChallenge.totalDays) {
            30 -> {
                viewModel.getThirtyDayExercise(day.toString())
            }
            60 -> {
                viewModel.getSixtyDayExercise(day.toString())
            }
            120 -> {
                viewModel.getOneTwentyDayExercise(day.toString())
            }
        }
        binding.dayTv.text = "Day ${day.toString()}"
    }

    private fun setObservers() {
        viewModel.exerciseDayLiveData.observe(viewLifecycleOwner) {
            when (it) {
                is ExerciseSuccess<*> -> {
                    val list = if(exerciseChallenge.totalDays == 30) (it.data as ThirtyDaysExerciseEntity).exercises as ArrayList<EachDayExerciseModel>
                    else if(exerciseChallenge.totalDays == 60) (it.data as SixtyDaysExerciseEntity).exercises as ArrayList<EachDayExerciseModel>
                    else (it.data as OneTwentyDaysExerciseEntity).exercises as ArrayList<EachDayExerciseModel>

                    val adapter = EachDayExerciseAdapter(requireContext(), object :
                        DiffUtil.ItemCallback<EachDayExerciseModel>() {
                        override fun areContentsTheSame(
                            oldItem: EachDayExerciseModel,
                            newItem: EachDayExerciseModel
                        ): Boolean {
                            return oldItem.day.equals(newItem.day)
                        }

                        override fun areItemsTheSame(
                            oldItem: EachDayExerciseModel,
                            newItem: EachDayExerciseModel
                        ): Boolean {
                            return oldItem == newItem
                        }
                    })

                    binding.exerciseRv.adapter = adapter
                    binding.exerciseRv.layoutManager = LinearLayoutManager(requireContext())

                    adapter.submitList(list)
                }

                is ExerciseError -> {
                    Toast(context).apply {
                        showError(this, requireContext(), binding.root as ViewGroup, it.error)
                    }
                }

                else -> {}
            }
        }

        viewModel.eachDayDetails.observe(viewLifecycleOwner) {
            when(it) {
                is Success<*> -> {
                    Toast(context).apply {
                        showError(this, requireContext(), binding.root as ViewGroup, (it.data as EachDayExerciseModel).description.toString())
                    }
                }

                else -> {}
            }
        }
    }

    private fun setListeners() {
        binding.ivUp.setOnClickListener {
            requireActivity().onBackPressed()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        (requireActivity() as MainActivity).binding.bottomNavViewCv.visibility = View.VISIBLE
    }
}