package com.bytezaptech.jawlineexercise_faceyoga.ui.settings

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bytezaptech.jawlineexercise_faceyoga.R
import com.bytezaptech.jawlineexercise_faceyoga.data.repositories.MainRepository
import com.bytezaptech.jawlineexercise_faceyoga.databinding.FragmentSettingsBinding
import com.bytezaptech.jawlineexercise_faceyoga.ui.main.MainActivity
import com.bytezaptech.jawlineexercise_faceyoga.utils.MyApplication
import com.bytezaptech.jawlineexercise_faceyoga.utils.findNavControllerSafety
import javax.inject.Inject

class SettingsFragment : Fragment() {
    private lateinit var binding: FragmentSettingsBinding
    private lateinit var viewModel: SettingsViewModel
    @Inject
    lateinit var mainRepo: MainRepository

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity?.application as MyApplication).appComponent.inject(this)
        (requireActivity() as MainActivity).binding.bottomNavView.visibility = View.GONE
    }

    override fun onDestroy() {
        super.onDestroy()
        (requireActivity() as MainActivity).binding.bottomNavView.visibility = View.VISIBLE
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        requireActivity().enableEdgeToEdge()
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_settings, container, false);
        viewModel = ViewModelProvider(this, SettingsViewModelFactory(mainRepo))[SettingsViewModel::class.java]

        ViewCompat.setOnApplyWindowInsetsListener(binding.root)  { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        setListeners()
        return binding.root
    }

    private fun setListeners() {
        binding.profileTv.setOnClickListener {
            val action = SettingsFragmentDirections.actionSettingsToProfileDetailsFragment()
            findNavControllerSafety(R.id.settings)?.navigate(action)
        }

        binding.languageTv.setOnClickListener {
            val action = SettingsFragmentDirections.settingsToLanguage()
            findNavControllerSafety(R.id.settings)?.navigate(action)
        }

        binding.remindersTv.setOnClickListener {
            val action = SettingsFragmentDirections.settingsToSchedule()
            findNavControllerSafety(R.id.settings)?.navigate(action)
        }

        binding.restDurationTv.setOnClickListener {
            val action = SettingsFragmentDirections.settingsToRestDuration()
            findNavControllerSafety(R.id.settings)?.navigate(action)
        }
    }

}