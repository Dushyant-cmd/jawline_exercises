package com.bytezaptech.jawlineexercise_faceyoga.ui.exercise_details

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import com.bytezaptech.jawlineexercise_faceyoga.R
import com.bytezaptech.jawlineexercise_faceyoga.databinding.QuitDialogFragmentBinding
import com.bytezaptech.jawlineexercise_faceyoga.utils.MyApplication

class QuitDialogFragment : DialogFragment() {
    private lateinit var binding: QuitDialogFragmentBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.quit_dialog_fragment, container, false)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val application = (requireActivity().application as MyApplication)

        binding.quitBtn.setOnTouchListener(object: OnTouchListener {
            override fun onTouch(p0: View?, motionEvent: MotionEvent?): Boolean {
                if (motionEvent != null) {
                    when (motionEvent.action) {
                        MotionEvent.ACTION_DOWN -> {
                            application.scaleView(
                                binding.root,
                                true
                            )
                        }

                        MotionEvent.ACTION_UP -> {
                            application.scaleView(
                                binding.root,
                                false
                            )
                            findNavController().popBackStack()
                            findNavController().popBackStack()
                            dismiss()
                        }

                        MotionEvent.ACTION_CANCEL -> {
                            application.scaleView(
                                binding.root,
                                false
                            )
                        }
                    }
                }

                return true
            }
        })

        binding.cancelBtn.setOnClickListener {
            dismiss()
        }
        return binding.root
    }
}