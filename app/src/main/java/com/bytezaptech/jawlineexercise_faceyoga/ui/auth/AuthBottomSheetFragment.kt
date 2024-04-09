package com.bytezaptech.jawlineexercise_faceyoga.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.bytezaptech.jawlineexercise_faceyoga.R
import com.bytezaptech.jawlineexercise_faceyoga.databinding.AuthBottomSheetBinding
import com.bytezaptech.jawlineexercise_faceyoga.utils.MyApplication
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class AuthBottomSheetFragment : BottomSheetDialogFragment() {
    lateinit var binding: AuthBottomSheetBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.auth_bottom_sheet, container, false)

        setListeners()
        return binding.root
    }

    private fun setListeners() {
        binding.llSignBtn.setOnTouchListener(object: View.OnTouchListener {
            override fun onTouch(v: View, motionEvent: MotionEvent): Boolean {
                when(motionEvent.action) {
                    MotionEvent.ACTION_DOWN -> {
                        (requireActivity().application as MyApplication).scaleView(binding.llSignBtn, true)
                    }

                    MotionEvent.ACTION_UP -> {
                        (requireActivity().application as MyApplication).scaleView(binding.llSignBtn, false)
                        (activity as LoginAndSIgnUp).signUpOrSignInUser()
                        dismiss()
                    }
                }

                return true
            }
        })
    }
}