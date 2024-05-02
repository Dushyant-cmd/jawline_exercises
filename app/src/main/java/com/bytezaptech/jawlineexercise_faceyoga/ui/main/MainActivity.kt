package com.bytezaptech.jawlineexercise_faceyoga.ui.main

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.bytezaptech.jawlineexercise_faceyoga.R
import com.bytezaptech.jawlineexercise_faceyoga.databinding.ActivityMainBinding
import com.bytezaptech.jawlineexercise_faceyoga.utils.showSuccess
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    private var isBackPressed: Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        viewModel = ViewModelProvider(this, MainViewModelFactory(this))[MainViewModel::class.java]
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        when(isBackPressed) {
            true -> super.onBackPressed()
            false -> {
                Toast(this).apply {
                    showSuccess(this, this@MainActivity, binding.main, "Press again to exit")
                }
                isBackPressed = true
                lifecycleScope.launch {
                    delay(2000)
                    isBackPressed = false
                }
            }
        }
    }
}