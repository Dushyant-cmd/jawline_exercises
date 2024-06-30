package com.bytezaptech.jawlineexercise_faceyoga.ui.camera

import android.Manifest
import android.content.ContentValues
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bytezaptech.jawlineexercise_faceyoga.R
import com.bytezaptech.jawlineexercise_faceyoga.databinding.FragmentCameraBinding
import com.bytezaptech.jawlineexercise_faceyoga.utils.MyApplication
import com.bytezaptech.jawlineexercise_faceyoga.utils.findNavControllerSafety
import com.bytezaptech.jawlineexercise_faceyoga.utils.showAlertDialog
import com.bytezaptech.jawlineexercise_faceyoga.utils.showSuccess
import com.bytezaptech.jawlineexercise_faceyoga.utils.somethingWentWrong
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class CameraFragment : Fragment() {
    private lateinit var binding: FragmentCameraBinding
    private lateinit var imageCapture: ImageCapture
    private lateinit var cameraExecutor: ExecutorService
    private val args: CameraFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_camera, container, false)

        if (allPermissionGranted())
            setupCamera()
        else
            requestPermissions()

        setListeners()
        cameraExecutor = Executors.newSingleThreadExecutor()
        return binding.root
    }

    private fun setListeners() {
        binding.capBtn.setOnTouchListener { view: View, event: MotionEvent ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    (requireActivity().application as MyApplication).scaleView(binding.capBtn, true)
                }

                MotionEvent.ACTION_UP -> {
                    (requireActivity().application as MyApplication).scaleView(binding.capBtn, false)
                    takePhoto()
                }

                MotionEvent.ACTION_CANCEL -> {
                    (requireActivity().application as MyApplication).scaleView(binding.capBtn, true)
                }
            }

            true
        }
    }

    private fun takePhoto() {
        val imageCapture = imageCapture ?: return

        val name = SimpleDateFormat(FILE_NAME, Locale.US)
            .format(System.currentTimeMillis())
        val contentValues = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, name)
            put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
            if(Build.VERSION.SDK_INT > Build.VERSION_CODES.P) {
                put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/CameraX-Image")
            }
        }

        val outputOptions = ImageCapture.OutputFileOptions
            .Builder(requireActivity().contentResolver,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                contentValues)
            .build()

        imageCapture.takePicture(
            outputOptions,
            ContextCompat.getMainExecutor(requireActivity()),
            object : ImageCapture.OnImageSavedCallback {
                override fun onError(exc: ImageCaptureException) {
                    somethingWentWrong(requireActivity(), findNavController(), "Something went wrong", "${exc.message}", "ok")
                }

                override fun onImageSaved(output: ImageCapture.OutputFileResults){
                    args.viewModel.completeDayExercise(args.data, output.savedUri.toString())
                    Toast(requireContext()).apply {
                        showSuccess(
                            this,
                            requireContext(),
                            binding.root as ViewGroup,
                            "Day ${args.data.daysCompleted} Finished"
                        )

                        findNavControllerSafety(R.id.exerciseDoingFragment)?.navigate(
                            CameraFragmentDirections.exerciseDoingToHomeFragment()
                        )
                    }
                }
            }
        )
    }

    private fun allPermissionGranted() = REQUEST_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(requireContext(), it) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestPermissions() {
        permissionLauncher.launch(REQUEST_PERMISSIONS)
    }

    private fun setupCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(requireContext())

        cameraProviderFuture.addListener({
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()

            // Preview
            val preview = Preview.Builder()
                .build()
                .also {
                    it.setSurfaceProvider(binding.previewView.surfaceProvider)
                }

            imageCapture = ImageCapture.Builder()
                .build()

            // Select back camera as a default
            val cameraSelector = CameraSelector.DEFAULT_FRONT_CAMERA

            try {
                // Unbind use cases before rebinding
                cameraProvider.unbindAll()

                // Bind use cases to camera
                cameraProvider.bindToLifecycle(
                    this, cameraSelector, preview, imageCapture)

            } catch(exc: Exception) {
                somethingWentWrong(requireActivity(), findNavController(), "Something went wrong", "${exc.message}", "ok")
            }

        }, ContextCompat.getMainExecutor(requireContext()))
    }

    val permissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            var permissionGranted = true

            permissions.entries.forEach { it ->
                if (it.key in REQUEST_PERMISSIONS && !it.value)
                    permissionGranted = false
            }

            if (!permissionGranted) {
                val dialog = showAlertDialog(
                    requireContext(),
                    "We need your permission to store your growth record.",
                    "Ask again",
                    "skip"
                )

                dialog.findViewById<Button>(R.id.quit_btn)?.setOnClickListener {
                    requestPermissions()
                }

                dialog.findViewById<Button>(R.id.cancel_btn)?.setOnClickListener {
                    findNavControllerSafety(R.id.cameraFragment)?.popBackStack()
                }
            } else
                setupCamera()
        }

    companion object {
        val FILE_NAME = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
            .format(System.currentTimeMillis()) + "_growth"
        val REQUEST_PERMISSIONS = mutableListOf(
            Manifest.permission.CAMERA,
            Manifest.permission.RECORD_AUDIO
        )
            .apply {
                if (Build.VERSION.SDK_INT > Build.VERSION_CODES.P)
                    add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            }.toTypedArray()
    }
}