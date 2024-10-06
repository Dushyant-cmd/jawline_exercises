package com.bytezaptech.jawlineexercise_faceyoga.ui.onboard_details

import android.Manifest
import android.app.TimePickerDialog
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TimePicker
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.bytezaptech.jawlineexercise_faceyoga.R
import com.bytezaptech.jawlineexercise_faceyoga.databinding.FragmentScheduleBinding
import com.bytezaptech.jawlineexercise_faceyoga.ui.main.MainActivity
import com.bytezaptech.jawlineexercise_faceyoga.utils.AlarmNotificationWorkRequest
import com.bytezaptech.jawlineexercise_faceyoga.utils.findNavControllerSafety
import com.bytezaptech.jawlineexercise_faceyoga.utils.getFormattedDate
import com.bytezaptech.jawlineexercise_faceyoga.utils.showSuccess
import java.util.Calendar
import java.util.Date
import java.util.concurrent.TimeUnit
import kotlin.math.abs

class ScheduleFragment : Fragment() {
    private lateinit var binding: FragmentScheduleBinding
    private val timeFormat = "hh:mm a"
    private lateinit var cal: Calendar
    private lateinit var dateDialog: TimePickerDialog

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity() as MainActivity).binding.bottomNavView.visibility = View.GONE
    }

    override fun onDestroy() {
        super.onDestroy()
        (requireActivity() as MainActivity).binding.bottomNavView.visibility = View.VISIBLE
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        requireActivity().enableEdgeToEdge()
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_schedule,
            container,
            false
        )
        ViewCompat.setOnApplyWindowInsetsListener(
            binding.root
        ) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        cal = Calendar.getInstance()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
            if (ContextCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.POST_NOTIFICATIONS
                ) == PackageManager.PERMISSION_DENIED
            ) {
                requestPermissions(arrayOf(Manifest.permission.POST_NOTIFICATIONS), 101)
            }
        binding.timeTv.setText(cal.getFormattedDate(System.currentTimeMillis(), timeFormat))
        setListeners()
        return binding.root
    }

    private fun setListeners() {

        binding.nextBtn.setOnClickListener {
            (requireActivity() as OnboardDetailsActivity).viewModel.submitExerciseTime(
                binding.timeTv.text.toString()
            )
        }

        binding.switchCompat.setOnCheckedChangeListener { p0, p1 ->
            if (p1)
                timePicker()
        }
    }

    private fun timePicker() {
        val workManager = WorkManager.getInstance(requireContext())

        dateDialog = TimePickerDialog(requireContext(), { _: TimePicker, i: Int, i1: Int ->
            cal.set(Calendar.HOUR_OF_DAY, i)
            cal.set(Calendar.MINUTE, i1)
            binding.timeTv.text = cal.getFormattedDate(cal.timeInMillis, timeFormat)
            binding.switchCompat.isChecked = true

            val selDate = cal.time
            val currDate = Date(System.currentTimeMillis())

            val diffInMill = abs(currDate.time - selDate.time)
            val differenceInHours: Long = ((diffInMill / (60 * 60 * 1000)) % 24)

            val diffInMinutes = (diffInMill / (60 * 1000)) % 60

            val totalMin = (differenceInHours * 60) + diffInMinutes

            val worker =
                PeriodicWorkRequestBuilder<AlarmNotificationWorkRequest>(
                    totalMin,
                    TimeUnit.SECONDS
                ).build()
            workManager.enqueueUniquePeriodicWork("Alarm", ExistingPeriodicWorkPolicy.KEEP, worker)
        }, cal.get(Calendar.HOUR), cal.get(Calendar.MINUTE), true)

        dateDialog.setOnCancelListener {
            binding.switchCompat.isChecked = false
            workManager.cancelAllWork()//Cancel all running worker request. can also use tag to cancel
            //work request.
        }

        dateDialog.show()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        for (value in grantResults) {
            if (value == PackageManager.PERMISSION_DENIED) {
                try {
                    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                    intent.setData(Uri.parse("package:${requireContext().packageName}"))
                    startActivity(intent)
                } catch (e: ActivityNotFoundException) {
                    val intent = Intent(Settings.ACTION_MANAGE_APPLICATIONS_SETTINGS)
                    startActivity(intent)
                }

                Toast.makeText(requireContext(), "Please Allow Permissions", Toast.LENGTH_LONG)
                    .show()
            }
        }
    }
}