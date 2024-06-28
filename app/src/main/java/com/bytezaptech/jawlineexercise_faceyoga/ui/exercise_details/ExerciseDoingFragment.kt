package com.bytezaptech.jawlineexercise_faceyoga.ui.exercise_details

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Environment
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.bytezaptech.jawlineexercise_faceyoga.R
import com.bytezaptech.jawlineexercise_faceyoga.data.repositories.MainRepository
import com.bytezaptech.jawlineexercise_faceyoga.databinding.FragmentExerciseDoingBinding
import com.bytezaptech.jawlineexercise_faceyoga.ui.home.HomeViewModel
import com.bytezaptech.jawlineexercise_faceyoga.ui.home.HomeViewModelFactory
import com.bytezaptech.jawlineexercise_faceyoga.ui.main.MainActivity
import com.bytezaptech.jawlineexercise_faceyoga.utils.MyApplication
import com.bytezaptech.jawlineexercise_faceyoga.utils.Success
import com.bytezaptech.jawlineexercise_faceyoga.utils.findNavControllerSafety
import com.bytezaptech.jawlineexercise_faceyoga.utils.showAlertDialog
import com.bytezaptech.jawlineexercise_faceyoga.utils.showMessageDialog
import com.bytezaptech.jawlineexercise_faceyoga.utils.showSuccess
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import javax.inject.Inject

class ExerciseDoingFragment : Fragment() {
    private lateinit var binding: FragmentExerciseDoingBinding
    private lateinit var viewModel: HomeViewModel
    private var countDown: CountDownTimer? = null
    private var currEx: Int = 0
    private var growthPhotoPath = ""

    @Inject
    lateinit var mainRepo: MainRepository
    private val args: ExerciseDoingFragmentArgs by navArgs()//by lazy operator which assign args variable when it comes in use.

    override fun onAttach(context: Context) {
        super.onAttach(context)
        ((activity as MainActivity).application as MyApplication).appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_exercise_doing, container, false)
        viewModel =
            ViewModelProvider(this, HomeViewModelFactory(mainRepo))[HomeViewModel::class.java]

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        viewModel.nextExerciseDoing()
        setObservers()
        setListeners()
        return binding.root
    }

    private fun setObservers() {
        viewModel.exerciseDoingLiveData.observe(viewLifecycleOwner) {
            when (it) {
                is Success<*> -> {
                    binding.cvPrev.visibility = View.VISIBLE
                    binding.cvNext.visibility = View.VISIBLE
                    binding.finishBtn.visibility = View.GONE

                    // hold current exercise
                    currEx = it.data as Int + 1

                    if (it.data == 0) binding.cvPrev.visibility = View.INVISIBLE
                    else if (it.data == (args.data.size - 1)) {
                        binding.cvPrev.visibility = View.GONE
                        binding.cvNext.visibility = View.GONE
                        binding.controlLy.visibility = View.GONE
                        binding.finishBtn.visibility = View.VISIBLE
                    }

                    val exercise = args.data[it.data]
                    binding.exerciseLv.setAnimation(exercise.img!!)
                    binding.titleTv.text = exercise.title
                    binding.tvSec.text = exercise.duration

                    binding.pb.max = exercise.duration?.toInt() ?: 0
                    val durMillis = exercise.duration?.toLong()?.times(1000) ?: 0
                    startTimer((durMillis))

                    binding.totalExTv.text = "${it.data + 1} / ${args.data.size}"
                }

                else -> {}
            }
        }
    }

    private fun startTimer(duration: Long) {
        countDown?.cancel()
        countDown = object : CountDownTimer(duration, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val sec = ((millisUntilFinished / 1000).toInt())
                binding.pb.setProgress(sec, true)
                binding.tvSec.text = if (sec < 10) "0$sec" else sec.toString()
            }

            override fun onFinish() {
                //Rest Fragment
                activity?.let {
                    if (currEx == (args.data.size - 1)) {
                        completedExercise()
                    } else {
                        val action = ExerciseDoingFragmentDirections.exerciseDoingToWaitFragment(
                            args.data,
                            args.exerciseChallenge,
                            currEx
                        )
                        findNavControllerSafety(R.id.exerciseDoingFragment)?.navigate(action)
                    }
                }
            }
        }

        countDown?.start()
    }

    private fun setListeners() {
        binding.finishBtn.setOnClickListener {
            completedExercise()
        }

        binding.playIv.setOnClickListener {
            binding.pauseIv.visibility = View.VISIBLE
            binding.playIv.visibility = View.GONE

            binding.exerciseLv.playAnimation()
            val durMillis = binding.tvSec.text.toString().toLong().times(1000)
            startTimer(durMillis)
        }

        binding.pauseIv.setOnClickListener {
            binding.pauseIv.visibility = View.GONE
            binding.playIv.visibility = View.VISIBLE

            binding.exerciseLv.pauseAnimation()
            countDown?.cancel()
        }

        binding.ivUp.setOnClickListener {
            quitDialog()
        }

        binding.cvNext.setOnClickListener {
            val action = ExerciseDoingFragmentDirections.exerciseDoingToWaitFragment(
                args.data,
                args.exerciseChallenge,
                currEx
            )
            findNavControllerSafety(R.id.exerciseDoingFragment)?.navigate(action)
        }

        binding.cvPrev.setOnClickListener {
            viewModel.prevExerciseDoing()
        }
    }

    private fun completedExercise() {
        //Check if day is divided by 6 then ask for face photo of user to track growth.
        when (args.exerciseChallenge.daysCompleted?.rem(6)) {
            0 -> {
                val dialog = showMessageDialog(
                    requireContext(),
                    "Save Growth Record",
                    "Please capture your growth till now",
                    "Capture"
                )

                dialog.findViewById<TextView>(R.id.tv_ok_btn)?.setOnClickListener {
                    captureImage()
                    dialog.dismiss()
                }
            }

            else -> {
                saveDayExercise("")
            }
        }
    }

    private fun saveDayExercise(growthImg: String) {
        viewModel.completeDayExercise(args.exerciseChallenge, growthImg)
        Toast(requireContext()).apply {
            showSuccess(
                this,
                requireContext(),
                binding.root as ViewGroup,
                "Day ${args.exerciseChallenge.daysCompleted} Finished"
            )

            findNavControllerSafety(R.id.exerciseDoingFragment)?.navigate(
                ExerciseDoingFragmentDirections.exerciseDoingToHomeFragment()
            )
        }
    }

    private fun captureImage() {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                android.Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            //get storage dir, then create temp file, then get uri from content provider(FileProvider), then store path in growthImg in contract
            val storageDir =
                requireActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES)
            val file =
                File.createTempFile("growth${System.currentTimeMillis()}", ".jpg", storageDir)
            growthPhotoPath = file.absolutePath

            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            val uri = FileProvider.getUriForFile(
                requireContext(),
                activity?.packageName!!,
                file
            )
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            contract.launch(intent)
        } else
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(android.Manifest.permission.CAMERA),
                1
            )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 1 && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            captureImage()
        } else {
            val dialog = showMessageDialog(
                requireContext(),
                "Save Growth Record",
                "Please capture your growth till now",
                "Capture"
            )

            dialog.findViewById<TextView>(R.id.tv_ok_btn)?.setOnClickListener {
                captureImage()
                dialog.dismiss()
            }
        }
    }

    private val contract: ActivityResultLauncher<Intent> =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            when (it.resultCode) {
                Activity.RESULT_OK -> {
                    val uri = Uri.fromFile(File(growthPhotoPath))

                    val file = File(
                        requireContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES),
                        "growth" + System.currentTimeMillis() + ".jpeg"
                    )

                    val inputStream = requireContext().contentResolver.openInputStream(uri)
                    val bitmap = BitmapFactory.decodeStream(inputStream)

                    val bAOS = ByteArrayOutputStream()
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bAOS)
                    val bytes = bAOS.toByteArray()
                    val fileOutputStream = FileOutputStream(file)
                    fileOutputStream.write(bytes)
                    fileOutputStream.flush()
                    fileOutputStream.close()
                    bAOS.close()


                    if (file.exists()) {
                        saveDayExercise(uri.toString())
                    } else {
                        val dialog = showAlertDialog(
                            requireContext(),
                            "Please try again!",
                            "Try again",
                            "Skip"
                        )
                        dialog.findViewById<Button>(R.id.quit_btn)?.setOnClickListener {
                            captureImage()
                            dialog.dismiss()
                        }

                        dialog.findViewById<Button>(R.id.cancel_btn)?.setOnClickListener {
                            saveDayExercise("")
                            dialog.dismiss()
                        }
                    }
                }
            }
        }

    fun quitDialog() {
        val action = ExerciseDoingFragmentDirections.exerciseDoingQuitDialog(
            args.exerciseChallenge,
            args.exerciseChallenge.daysCompleted!!
        )
        findNavControllerSafety(R.id.exerciseDoingFragment)?.navigate(action)
    }
}