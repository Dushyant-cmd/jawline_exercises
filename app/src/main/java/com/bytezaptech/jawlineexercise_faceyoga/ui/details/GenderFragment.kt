package com.bytezaptech.jawlineexercise_faceyoga.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.bytezaptech.jawlineexercise_faceyoga.R
import com.bytezaptech.jawlineexercise_faceyoga.databinding.FragmentGenderBinding

class GenderFragment : Fragment() {
    private lateinit var binding: FragmentGenderBinding
    private var gender: String = ""
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_gender, container, false)
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.right)
            insets
        }
        setListeners()
        return binding.root
    }

    fun setListeners() {
        binding.maleCard.setOnClickListener {
            binding.maleCard.setCardBackgroundColor(ResourcesCompat.getColor(resources, R.color.color_primary, requireActivity().theme))
            binding.femaleCard.setCardBackgroundColor(ResourcesCompat.getColor(resources, R.color.white, requireActivity().theme))
            binding.otherCard.setCardBackgroundColor(ResourcesCompat.getColor(resources, R.color.white, requireActivity().theme))
            gender = "male"
        }
        binding.femaleCard.setOnClickListener {
            binding.maleCard.setCardBackgroundColor(ResourcesCompat.getColor(resources, R.color.white, requireActivity().theme))
            binding.femaleCard.setCardBackgroundColor(ResourcesCompat.getColor(resources, R.color.red, requireActivity().theme))
            binding.otherCard.setCardBackgroundColor(ResourcesCompat.getColor(resources, R.color.white, requireActivity().theme))
            gender = "female"
        }
        binding.otherCard.setOnClickListener {
            binding.maleCard.setCardBackgroundColor(ResourcesCompat.getColor(resources, R.color.white, requireActivity().theme))
            binding.femaleCard.setCardBackgroundColor(ResourcesCompat.getColor(resources, R.color.white, requireActivity().theme))
            binding.otherCard.setCardBackgroundColor(ResourcesCompat.getColor(resources, R.color.black, requireActivity().theme))
            gender = "other"
        }

        binding.nextBtn.setOnClickListener {
            (requireActivity() as OnboardDetailsActivity).viewModel.submitGender(gender)
        }
    }
}