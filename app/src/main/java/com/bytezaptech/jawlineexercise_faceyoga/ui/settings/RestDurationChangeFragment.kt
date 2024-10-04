package com.bytezaptech.jawlineexercise_faceyoga.ui.settings

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.bytezaptech.jawlineexercise_faceyoga.R
import com.bytezaptech.jawlineexercise_faceyoga.data.repositories.MainRepository
import com.bytezaptech.jawlineexercise_faceyoga.databinding.FragmentRestDurationChangeBinding
import com.bytezaptech.jawlineexercise_faceyoga.utils.MyApplication
import javax.inject.Inject

class RestDurationChangeFragment : Fragment() {
    private lateinit var binding: FragmentRestDurationChangeBinding
    private lateinit var viewModel: SettingsViewModel
    @Inject
    lateinit var mainRepo: MainRepository

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as MyApplication).appComponent.inject(this)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View?{
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_rest_duration_change, container, false)
        viewModel = ViewModelProvider(this, SettingsViewModelFactory(mainRepo))[SettingsViewModel::class.java]

        return binding.root
    }
}