package com.bytezaptech.jawlineexercise_faceyoga.ui.details

import android.app.TimePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TimePicker
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.bytezaptech.jawlineexercise_faceyoga.databinding.FragmentScheduleBinding
import com.bytezaptech.jawlineexercise_faceyoga.utils.getFormattedDate
import java.util.Calendar


class ScheduleFragment : Fragment() {
    private lateinit var binding: FragmentScheduleBinding
    private val timeFormat = "hh:mm a"
    private lateinit var cal: Calendar
    private lateinit var dateDialog: TimePickerDialog
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, com.bytezaptech.jawlineexercise_faceyoga.R.layout.fragment_schedule, container, false)
        cal = Calendar.getInstance()


        binding.timeTv.setText(cal.getFormattedDate(System.currentTimeMillis(), timeFormat))
        setListeners()
        return binding.root
    }

    private fun setListeners() {

        binding.nextBtn.setOnClickListener {
            (requireActivity() as OnboardDetailsActivity).viewModel.submitExerciseTime(binding.timeTv.text.toString())
        }

        binding.switchCompat.setOnCheckedChangeListener { p0, p1 ->
            if (p1)
                timePicker()
        }
    }

    private fun timePicker() {
        dateDialog = TimePickerDialog(requireContext(), { timePicker: TimePicker, i: Int, i1: Int ->
            cal.set(Calendar.HOUR_OF_DAY, i)
            cal.set(Calendar.MINUTE, i1)
            binding.timeTv.text = cal.getFormattedDate(cal.timeInMillis, timeFormat)
            binding.switchCompat.isChecked = true
        }, cal.get(Calendar.HOUR), cal.get(Calendar.MINUTE), true)

        dateDialog.setOnCancelListener {
            binding.switchCompat.isChecked = false
        }

        dateDialog.show()
    }

}