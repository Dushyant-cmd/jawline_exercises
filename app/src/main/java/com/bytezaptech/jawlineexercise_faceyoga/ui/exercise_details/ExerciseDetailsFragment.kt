package com.bytezaptech.jawlineexercise_faceyoga.ui.exercise_details

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.bytezaptech.jawlineexercise_faceyoga.adapters.EachDayExerciseAdapter
import com.bytezaptech.jawlineexercise_faceyoga.data.local.entities.ExerciseChallenge
import com.bytezaptech.jawlineexercise_faceyoga.data.local.entities.SixtyDaysExerciseEntity
import com.bytezaptech.jawlineexercise_faceyoga.data.local.entities.ThirtyDaysExerciseEntity
import com.bytezaptech.jawlineexercise_faceyoga.data.repositories.MainRepository
import com.bytezaptech.jawlineexercise_faceyoga.databinding.FragmentExerciseDetailsBinding
import com.bytezaptech.jawlineexercise_faceyoga.models.EachDayExerciseModel
import com.bytezaptech.jawlineexercise_faceyoga.ui.home.HomeViewModel
import com.bytezaptech.jawlineexercise_faceyoga.ui.home.HomeViewModelFactory
import com.bytezaptech.jawlineexercise_faceyoga.utils.MyApplication
import com.bytezaptech.jawlineexercise_faceyoga.utils.Success
import javax.inject.Inject

class ExerciseDetailsFragment : Fragment() {
    private lateinit var binding: FragmentExerciseDetailsBinding
    private lateinit var viewModel: HomeViewModel
    @Inject
    lateinit var mainRepository: MainRepository

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity() as MyApplication).appComponent.inject(this)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentExerciseDetailsBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this, HomeViewModelFactory(mainRepository))[HomeViewModel::class.java]
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        setupViews()
        setObservers()
        setListeners()
        return binding.root
    }

    private fun setObservers() {
        viewModel.exerciseDayLiveData.observe(viewLifecycleOwner) {
            when(it) {
                is Success<*> -> {
                    when(it.days) {
                        30 -> {
                            val list = it.data as ArrayList<ThirtyDaysExerciseEntity>

                            val adapter = EachDayExerciseAdapter(object : DiffUtil.ItemCallback<EachDayExerciseModel>() {
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

//                            adapter.submitList(list)
                        }
                        60 -> {
                            val list = it.data as ArrayList<SixtyDaysExerciseEntity>

                            val adapter = EachDayExerciseAdapter(object : DiffUtil.ItemCallback<EachDayExerciseModel>() {
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

//                            adapter.submitList(list)
                        }
                        120 -> {
                            val list = it.data as ArrayList<ThirtyDaysExerciseEntity>

                            val adapter = EachDayExerciseAdapter(object : DiffUtil.ItemCallback<EachDayExerciseModel>() {
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

//                            adapter.submitList(list)
                        }
                    }

//                    for (i in 0..10) {
//                        list.add(
//                            EachDayExerciseModel(
//                                i.toString(),
//                                R.raw.a_five,
//                                "Upside down",
//                                "60",
//                                "This is exercise"
//                            )
//                        )
//                    }
                }

                is Error -> {
                }

                else -> {}
            }
        }
    }

    private fun setListeners() {
        binding.ivUp.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun setupViews() {
        val day = arguments?.getInt("day")
        val exerciseChallenge = arguments?.getSerializable("exerciseChallenge") as ExerciseChallenge

        getChallengeDetails()

        binding.dayTv.text = "Day ${day.toString()}"
    }

    private fun getChallengeDetails() {
    }
}