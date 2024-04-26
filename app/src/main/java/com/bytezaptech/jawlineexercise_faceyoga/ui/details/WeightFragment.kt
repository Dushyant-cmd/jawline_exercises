package com.bytezaptech.jawlineexercise_faceyoga.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bytezaptech.jawlineexercise_faceyoga.databinding.FragmentWeightBinding

class WeightFragment : Fragment() {
    private lateinit var binding: FragmentWeightBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWeightBinding.inflate(inflater, container, false)

        setListeners()
        return binding.root
    }

    private fun setListeners() {
        binding.nextBtn.setOnClickListener {
            var weightType = ""
            when(binding.weightTogBtn.checkedButtonId) {
                binding.btnKgWay.id -> {
                    weightType = "KG"
                }

                binding.btnLbRound.id -> {
                    weightType = "LB"
                }

                else -> {}
            }
            (requireActivity() as OnboardDetailsActivity).viewModel.submitWeight(binding.weightEt.text.toString(), weightType)
        }
    }
}