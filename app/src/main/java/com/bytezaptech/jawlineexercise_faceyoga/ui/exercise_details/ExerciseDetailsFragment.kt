package com.bytezaptech.jawlineexercise_faceyoga.ui.exercise_details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.bytezaptech.jawlineexercise_faceyoga.R
import com.bytezaptech.jawlineexercise_faceyoga.adapters.EachDayExerciseAdapter
import com.bytezaptech.jawlineexercise_faceyoga.databinding.FragmentExerciseDetailsBinding
import com.bytezaptech.jawlineexercise_faceyoga.models.EachDayExerciseModel

class ExerciseDetailsFragment : Fragment() {
    private lateinit var binding: FragmentExerciseDetailsBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentExerciseDetailsBinding.inflate(inflater, container, false)

        setupViews()
        setListeners()
        return binding.root
    }

    private fun setListeners() {
        binding.ivUp.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun setupViews() {
        val day = arguments?.getInt("day")
        val exerciseChallenge = arguments?.getSerializable("exerciseChallenge")

        val list = ArrayList<EachDayExerciseModel>()
        for (i in 0..10) {
            list.add(
                EachDayExerciseModel(
                    i.toString(),
                    R.raw.a_five,
                    "Upside down",
                    "60",
                    "This is exercise"
                )
            )
        }

        val adapter =
            EachDayExerciseAdapter(object : DiffUtil.ItemCallback<EachDayExerciseModel>() {
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

        binding.dayTv.text = "Day ${day.toString()}"
    }
}