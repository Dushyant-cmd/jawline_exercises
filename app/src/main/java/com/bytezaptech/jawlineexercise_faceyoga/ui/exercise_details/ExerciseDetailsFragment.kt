package com.bytezaptech.jawlineexercise_faceyoga.ui.exercise_details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bytezaptech.jawlineexercise_faceyoga.databinding.FragmentExerciseDetailsBinding

class ExerciseDetailsFragment : Fragment() {
    private lateinit var binding: FragmentExerciseDetailsBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentExerciseDetailsBinding.inflate(inflater, container, false)

        setupViews()
        return binding.root
    }

    private fun setupViews() {
        val day = arguments?.getInt("day")
        val exerciseChallenge = arguments?.getSerializable("exerciseChallenge")

        binding.text.text = exerciseChallenge.toString()
    }
}