package com.bytezaptech.jawlineexercise_faceyoga.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.bytezaptech.jawlineexercise_faceyoga.R
import com.bytezaptech.jawlineexercise_faceyoga.data.repositories.MainRepository
import com.bytezaptech.jawlineexercise_faceyoga.databinding.FragmentThirtyDaysBinding
import javax.inject.Inject

class ThirtyDaysFragment : Fragment() {
    private lateinit var binding: FragmentThirtyDaysBinding
    private lateinit var viewModel: HomeViewModel
    @Inject
    lateinit var mainRepository: MainRepository
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_thirty_days, container, false)
        viewModel = ViewModelProvider(this, HomeViewModelFactory(mainRepository))[HomeViewModel::class.java]
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        return binding.root
    }

}