package com.bytezaptech.jawlineexercise_faceyoga.ui.onboard_details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.bytezaptech.jawlineexercise_faceyoga.R
import com.bytezaptech.jawlineexercise_faceyoga.databinding.FragmentAgeBinding

class AgeFragment : Fragment() {
    private lateinit var binding: FragmentAgeBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_age, container, false)

        setListeners()
        return binding.root
    }

    private fun setListeners() {
        binding.seekbar.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                binding.ageTv.text = "${progress}"
                binding.ageTv.textSize = (progress + 1).toFloat()
                when(progress) {
                    in 15..30 -> binding.ageTv.setTextColor(ResourcesCompat.getColor(requireActivity().resources, R.color.light_grey, requireActivity().theme))
                    in 30..45 -> binding.ageTv.setTextColor(ResourcesCompat.getColor(requireActivity().resources, R.color.violet_light, requireActivity().theme))
                    in 45..60 -> binding.ageTv.setTextColor(ResourcesCompat.getColor(requireActivity().resources, R.color.color_primary, requireActivity().theme))
                    in 60..75 -> binding.ageTv.setTextColor(ResourcesCompat.getColor(requireActivity().resources, R.color.red, requireActivity().theme))
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })
        binding.nextBtn.setOnClickListener {
            (requireActivity() as OnboardDetailsActivity).viewModel.submitAge(binding.ageTv.text.toString())
        }
    }
}