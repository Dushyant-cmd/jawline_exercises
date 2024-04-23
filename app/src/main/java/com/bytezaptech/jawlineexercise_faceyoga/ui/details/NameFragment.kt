package com.bytezaptech.jawlineexercise_faceyoga.ui.details

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.bytezaptech.jawlineexercise_faceyoga.R
import com.bytezaptech.jawlineexercise_faceyoga.databinding.FragmentNameBinding

class NameFragment : Fragment() {
    private lateinit var binding: FragmentNameBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_name, container, false)
        setListeners()
        return binding.root
    }

    private fun setListeners() {
        binding.nextBtn.setOnClickListener {
            (requireActivity() as OnboardDetailsActivity).viewModel.submit(binding.nameEt.text.toString())
        }
    }
}