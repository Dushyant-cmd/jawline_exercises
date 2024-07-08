package com.bytezaptech.jawlineexercise_faceyoga.ui.camera

import android.Manifest
import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.provider.MediaStore.Images
import android.provider.MediaStore.Video.Media
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
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bytezaptech.jawlineexercise_faceyoga.R
import com.bytezaptech.jawlineexercise_faceyoga.data.repositories.MainRepository
import com.bytezaptech.jawlineexercise_faceyoga.databinding.FragmentCameraBinding
import com.bytezaptech.jawlineexercise_faceyoga.ui.home.HomeViewModel
import com.bytezaptech.jawlineexercise_faceyoga.ui.home.HomeViewModelFactory
import com.bytezaptech.jawlineexercise_faceyoga.utils.MyApplication
import com.bytezaptech.jawlineexercise_faceyoga.utils.findNavControllerSafety
import com.bytezaptech.jawlineexercise_faceyoga.utils.showAlertDialog
import com.bytezaptech.jawlineexercise_faceyoga.utils.showSuccess
import com.bytezaptech.jawlineexercise_faceyoga.utils.somethingWentWrong
import java.io.File
import java.io.FileNotFoundException
import java.io.IOException
import java.io.OutputStream
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import javax.inject.Inject


class CameraFragment : Fragment() {
    private lateinit var binding: FragmentCameraBinding
    private lateinit var imageCapture: ImageCapture
    private lateinit var cameraExecutor: ExecutorService

    @Inject
    lateinit var mainRepo: MainRepository
    private val args: CameraFragmentArgs by navArgs()
    private lateinit var viewModel: HomeViewModel

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as MyApplication).appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_camera, container, false)
        viewModel =
            ViewModelProvider(this, HomeViewModelFactory(mainRepo))[HomeViewModel::class.java]

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { view, insets ->
            val systemBar = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.setPadding(systemBar.left, systemBar.top, systemBar.right, systemBar.bottom)
            insets
        }

        if (allPermissionGranted())
            setupCamera()
        else
            requestPermissions()

        setListeners()
        cameraExecutor = Executors.newSingleThreadExecutor()
        return binding.root
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setListeners() {
        binding.capBtn.setOnTouchListener { view: View, event: MotionEvent ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    (requireActivity().application as MyApplication).scaleView(binding.capBtn, true)
                }

                MotionEvent.ACTION_UP -> {
                    (requireActivity().application as MyApplication).scaleView(
                        binding.capBtn,
                        false
                    )
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

        val contentValues = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, FILE_NAME)
            put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.P) {
                put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/jawline_bytezaptech")
            }
        }

        val outputOptions = ImageCapture.OutputFileOptions
            .Builder(
                requireActivity().contentResolver,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                contentValues
            )
            .build()

        imageCapture.takePicture(
            outputOptions,
            ContextCompat.getMainExecutor(requireActivity()),
            object : ImageCapture.OnImageSavedCallback {
                override fun onError(exc: ImageCaptureException) {
                    somethingWentWrong(
                        requireActivity(),
                        findNavController(),
                        "Something went wrong",
                        "${exc.message}",
                        "ok"
                    )
                }

                override fun onImageSaved(output: ImageCapture.OutputFileResults) {
                    saveDayExercise(output.savedUri.toString())
                }
            }
        )
    }

    private fun setupViews(growthImg: Uri?) {
        binding.previewIv.setImageURI(growthImg)
        binding.previewView.visibility = View.GONE
        binding.capBtn.visibility = View.GONE
        binding.btnLy.visibility = View.VISIBLE
        binding.previewIv.visibility = View.VISIBLE

        binding.retryIv.setOnClickListener {
            //Delete previous picture
            val picturesDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
            File(picturesDir, growthImg.toString()).deleteRecursively()

            //Setup Views.
            binding.previewIv.setImageURI(null)
            binding.previewIv.visibility = View.GONE
            binding.btnLy.visibility = View.GONE
            binding.previewView.visibility = View.VISIBLE
            binding.capBtn.visibility = View.VISIBLE
        }

        binding.selectIv.setOnClickListener {
            saveDayExercise(growthImg.toString())
        }
    }

    private fun saveDayExercise(growthImg: String) {
        viewModel.completeDayExercise(args.data, growthImg)
        Toast(requireContext()).apply {
            showSuccess(
                this,
                requireContext(),
                binding.root as ViewGroup,
                "Day ${args.data.daysCompleted} Finished"
            )

            findNavControllerSafety(R.id.cameraFragment)?.navigate(
                CameraFragmentDirections.exerciseDoingToHomeFragment()
            )
        }
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
                    this, cameraSelector, preview, imageCapture
                )

            } catch (exc: Exception) {
                somethingWentWrong(
                    requireActivity(),
                    findNavController(),
                    "Something went wrong",
                    "${exc.message}",
                    "ok"
                )
            }

        }, ContextCompat.getMainExecutor(requireContext()))
    }

    private val permissionLauncher =
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
                    dialog.dismiss()
                }

                dialog.findViewById<Button>(R.id.cancel_btn)?.setOnClickListener {
                    val action = CameraFragmentDirections.exerciseDoingToHomeFragment()
                    findNavControllerSafety(R.id.cameraFragment)?.navigate(action)
                    dialog.dismiss()
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
                if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P)
                    add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            }.toTypedArray()
    }
}