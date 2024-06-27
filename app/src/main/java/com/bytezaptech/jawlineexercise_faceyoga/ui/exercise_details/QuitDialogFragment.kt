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
import androidx.navigation.fragment.navArgs
import com.bytezaptech.jawlineexercise_faceyoga.R
import com.bytezaptech.jawlineexercise_faceyoga.databinding.QuitDialogFragmentBinding
import com.bytezaptech.jawlineexercise_faceyoga.utils.MyApplication
import com.bytezaptech.jawlineexercise_faceyoga.utils.findNavControllerSafety

class QuitDialogFragment : DialogFragment() {
    private lateinit var binding: QuitDialogFragmentBinding
    private lateinit var application: MyApplication
    private val args: QuitDialogFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.quit_dialog_fragment, container, false)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        application = (requireActivity().application as MyApplication)

        setListeners()
        return binding.root
    }

    private fun setListeners() {
        binding.quitBtn.setOnTouchListener(object: OnTouchListener {
            override fun onTouch(p0: View?, motionEvent: MotionEvent?): Boolean {
                if (motionEvent != null) {
                    when (motionEvent.action) {
                        MotionEvent.ACTION_DOWN -> {
                            application.scaleView(
                                binding.quitBtn,
                                true
                            )
                        }

                        MotionEvent.ACTION_UP -> {
                            application.scaleView(
                                binding.quitBtn,
                                false
                            )
                            findNavControllerSafety(R.id.quitDialogFragment)?.navigate(QuitDialogFragmentDirections.quitToExerciseDetailsDialog(args.exerciseChallenge, args.currentExercise))
                        }

                        MotionEvent.ACTION_CANCEL -> {
                            application.scaleView(
                                binding.quitBtn,
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
    }
}