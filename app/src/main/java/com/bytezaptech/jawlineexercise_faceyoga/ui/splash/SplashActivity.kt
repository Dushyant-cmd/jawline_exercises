package com.bytezaptech.jawlineexercise_faceyoga.ui.splash

import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.bytezaptech.jawlineexercise_faceyoga.R
import com.bytezaptech.jawlineexercise_faceyoga.data.local.SharedPref
import com.bytezaptech.jawlineexercise_faceyoga.data.repositories.MainRepository
import com.bytezaptech.jawlineexercise_faceyoga.databinding.ActivitySplashBinding
import com.bytezaptech.jawlineexercise_faceyoga.ui.auth.LoginAndSIgnUp
import com.bytezaptech.jawlineexercise_faceyoga.ui.details.OnboardDetailsActivity
import com.bytezaptech.jawlineexercise_faceyoga.ui.main.MainActivity
import com.bytezaptech.jawlineexercise_faceyoga.utils.Constants
import com.bytezaptech.jawlineexercise_faceyoga.utils.MyApplication
import com.bytezaptech.jawlineexercise_faceyoga.utils.Success
import javax.inject.Inject

class SplashActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding
    @Inject
    lateinit var sharedPref: SharedPref
    @Inject
    lateinit var mainRepo: MainRepository
    lateinit var viewModel: SplashViewModel

    override fun onCreate(saveInstanceState: Bundle?) {
        (application as MyApplication).appComponent.inject(this)
        super.onCreate(saveInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        enableEdgeToEdge()
        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash)
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        viewModel = ViewModelProvider(this, SplashViewModelFactory(mainRepo))[SplashViewModel::class.java]

        viewModel.isUserLoggedIn()
        setObservers()
    }

    private fun setObservers() {
        viewModel.authAuthLiveData.observe(this) {
            if (it is Success<*>) {
                val isLogin = (it as Success<Boolean>).data
                when (isLogin) {
                    true -> {
                        if(sharedPref.getBoolean(Constants.isDetailFilled)) {
                            val intent = Intent(this, MainActivity::class.java)
                            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                            startActivity(intent)
                        } else {
                            val intent = Intent(this, OnboardDetailsActivity::class.java)
                            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                            startActivity(intent)
                        }
                    }

                    else -> {
                        val intent = Intent(this, LoginAndSIgnUp::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                        startActivity(intent)
                    }
                }
            }
        }
    }
}