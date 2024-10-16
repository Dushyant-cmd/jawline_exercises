package com.bytezaptech.jawlineexercise_faceyoga.ui.settings

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bytezaptech.jawlineexercise_faceyoga.R
import com.bytezaptech.jawlineexercise_faceyoga.data.repositories.MainRepository
import com.bytezaptech.jawlineexercise_faceyoga.databinding.FragmentRestDurationChangeBinding
import com.bytezaptech.jawlineexercise_faceyoga.ui.main.MainActivity
import com.bytezaptech.jawlineexercise_faceyoga.utils.MyApplication
import com.bytezaptech.jawlineexercise_faceyoga.utils.Success
import com.bytezaptech.jawlineexercise_faceyoga.utils.showSuccess
import javax.inject.Inject

class RestDurationChangeFragment : Fragment() {
    private lateinit var binding: FragmentRestDurationChangeBinding
    private lateinit var viewModel: SettingsViewModel
    @Inject
    lateinit var mainRepo: MainRepository

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as MyApplication).appComponent.inject(this)
        if (requireActivity() is MainActivity) (requireActivity() as MainActivity).binding.bottomNavView.visibility = View.GONE
    }

    override fun onDestroy() {
        super.onDestroy()
        if (requireActivity() is MainActivity) (requireActivity() as MainActivity).binding.bottomNavView.visibility = View.VISIBLE
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_rest_duration_change, container, false)
        viewModel = ViewModelProvider(this, SettingsViewModelFactory(mainRepo))[SettingsViewModel::class.java]

        ViewCompat.setOnApplyWindowInsetsListener(
            binding.root
        ) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setObservers()
        setListeners()
        return binding.root
    }

    private fun setObservers() {
        viewModel.restDurLD.observe(viewLifecycleOwner) {
            when(it) {
                is Success<*> -> {
                    Toast(requireActivity()).apply {
                        showSuccess(this, requireContext(), binding.root as ViewGroup, it.data.toString())
                        val action = RestDurationChangeFragmentDirections.restDurationFragmentToSettings()
                        findNavController().navigate(action)
                    }
                }

                else -> {}
            }
        }
    }

    private fun setListeners() {
        binding.seekbar.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                binding.ageTv.text = "${progress}"
                binding.ageTv.textSize = (progress + 1).toFloat()
                when(progress) {
                    in 15..20 -> binding.ageTv.setTextColor(ResourcesCompat.getColor(requireActivity().resources, R.color.light_grey, requireActivity().theme))
                    in 20..30 -> binding.ageTv.setTextColor(ResourcesCompat.getColor(requireActivity().resources, R.color.violet_light, requireActivity().theme))
                    in 30..35 -> binding.ageTv.setTextColor(ResourcesCompat.getColor(requireActivity().resources, R.color.color_primary, requireActivity().theme))
                    in 35..45 -> binding.ageTv.setTextColor(ResourcesCompat.getColor(requireActivity().resources, R.color.red, requireActivity().theme))
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }

        })

        binding.nextBtn.setOnClickListener {
            viewModel.changeRestDuration(binding.ageTv.text.toString().toLong().times(1000))
        }
    }
}