package com.bytezaptech.jawlineexercise_faceyoga.ui.exercise_details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bytezaptech.jawlineexercise_faceyoga.R
import com.bytezaptech.jawlineexercise_faceyoga.data.repositories.MainRepository
import com.bytezaptech.jawlineexercise_faceyoga.databinding.FragmentExerciseWaitBinding
import com.bytezaptech.jawlineexercise_faceyoga.ui.home.HomeViewModel
import com.bytezaptech.jawlineexercise_faceyoga.ui.home.HomeViewModelFactory
import javax.inject.Inject

class ExerciseWaitFragment : Fragment() {
    private lateinit var binding: FragmentExerciseWaitBinding
    private lateinit var viewModel: HomeViewModel
    @Inject
    lateinit var mainRepository: MainRepository
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_exercise_wait, container, false)
        viewModel = ViewModelProvider(this, HomeViewModelFactory(mainRepository))[HomeViewModel::class.java]

        return binding.root
    }
}