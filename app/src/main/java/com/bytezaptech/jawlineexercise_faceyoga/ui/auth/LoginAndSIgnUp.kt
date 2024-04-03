package com.bytezaptech.jawlineexercise_faceyoga.ui.auth

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.MotionEvent
import android.view.View.OnTouchListener
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.bytezaptech.jawlineexercise_faceyoga.R
import com.bytezaptech.jawlineexercise_faceyoga.data.repositories.AuthRepository
import com.bytezaptech.jawlineexercise_faceyoga.databinding.ActivityLoginAndSignUpBinding
import com.bytezaptech.jawlineexercise_faceyoga.utils.MyApplication
import javax.inject.Inject


class LoginAndSIgnUp : AppCompatActivity() {
    private lateinit var binding: ActivityLoginAndSignUpBinding
    @Inject
    lateinit var authRepo: AuthRepository
    lateinit var viewModel: LoginAndSignUpViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        (application as MyApplication).appComponent.getAuthComponent().create().inject(this)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login_and_sign_up)
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        viewModel = ViewModelProvider(this, LoginAndSignUpViewModelFactory(authRepo))[LoginAndSignUpViewModel::class.java]

        setListeners()
    }

    private fun setListeners() {
        binding.btnSignUp.setOnTouchListener { v, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    (application as MyApplication).scaleView(binding.btnSignUp, true)
                }

                MotionEvent.ACTION_UP -> {
                    (application as MyApplication).scaleView(binding.btnSignUp, false)
                    val sheet = AuthBottomSheetFragment()
                    sheet.show(supportFragmentManager, "")
                }
            }
            true
        }

        binding.tvSignIn.setOnClickListener {
            val sheet = AuthBottomSheetFragment()
            sheet.show(supportFragmentManager, "")
        }
    }
}