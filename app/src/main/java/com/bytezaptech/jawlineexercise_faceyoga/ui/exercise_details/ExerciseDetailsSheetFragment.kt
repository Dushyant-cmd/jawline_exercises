package com.bytezaptech.jawlineexercise_faceyoga.ui.exercise_details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.bytezaptech.jawlineexercise_faceyoga.R
import com.bytezaptech.jawlineexercise_faceyoga.databinding.ExerciseDetailsSheetBinding
import com.bytezaptech.jawlineexercise_faceyoga.models.EachDayExerciseModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class ExerciseDetailsSheetFragment : BottomSheetDialogFragment() {
    private lateinit var binding: ExerciseDetailsSheetBinding

    override fun onCreateView(inflater: LayoutInflater, parent: ViewGroup?, saveInsState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.exercise_details_sheet, parent, false)

        val data = arguments?.getSerializable("data") as EachDayExerciseModel

        binding.durTv.text = data.duration + " SEC"
        binding.tvTitle.text = data.title
        binding.tvDesc.text = data.description
        binding.lottieIv.setAnimation(data.img!!)

        setListeners()
        return binding.root
    }

    private fun setListeners() {
        binding.gotItBtn.setOnClickListener {
            dismiss()
        }

        binding.animCv.setOnClickListener {
            binding.animCv.backgroundTintList = resources.getColorStateList(R.color.white)
            binding.videoCv.backgroundTintList = resources.getColorStateList(R.color.too_light_pink)
        }

        binding.videoCv.setOnClickListener {
            binding.animCv.backgroundTintList = resources.getColorStateList(R.color.too_light_pink)
            binding.videoCv.backgroundTintList = resources.getColorStateList(R.color.white)
        }
    }
}