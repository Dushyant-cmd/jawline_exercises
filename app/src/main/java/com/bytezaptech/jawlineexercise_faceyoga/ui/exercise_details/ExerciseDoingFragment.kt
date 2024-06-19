package com.bytezaptech.jawlineexercise_faceyoga.ui.exercise_details

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bytezaptech.jawlineexercise_faceyoga.R
import com.bytezaptech.jawlineexercise_faceyoga.data.repositories.MainRepository
import com.bytezaptech.jawlineexercise_faceyoga.databinding.FragmentExerciseDoingBinding
import com.bytezaptech.jawlineexercise_faceyoga.ui.home.HomeViewModel
import com.bytezaptech.jawlineexercise_faceyoga.ui.home.HomeViewModelFactory
import com.bytezaptech.jawlineexercise_faceyoga.ui.main.MainActivity
import com.bytezaptech.jawlineexercise_faceyoga.utils.MyApplication
import javax.inject.Inject

class ExerciseDoingFragment : Fragment() {
    private lateinit var binding: FragmentExerciseDoingBinding
    private lateinit var viewModel: HomeViewModel

    @Inject
    lateinit var mainRepo: MainRepository

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

        ViewCompat.setOnApplyWindowInsetsListener(binding.root)  { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setListeners()
        return binding.root
    }

    private fun setListeners() {
        binding.playIv.setOnClickListener {
            binding.pauseIv.visibility = View.VISIBLE
            binding.playIv.visibility = View.GONE
        }
        binding.pauseIv.setOnClickListener {
            binding.pauseIv.visibility = View.GONE
            binding.playIv.visibility = View.VISIBLE
        }
    }

}