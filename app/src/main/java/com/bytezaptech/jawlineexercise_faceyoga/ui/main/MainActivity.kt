package com.bytezaptech.jawlineexercise_faceyoga.ui.main

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.bytezaptech.jawlineexercise_faceyoga.R
import com.bytezaptech.jawlineexercise_faceyoga.databinding.ActivityMainBinding
import com.bytezaptech.jawlineexercise_faceyoga.ui.exercise_details.ExerciseDoingFragment
import com.bytezaptech.jawlineexercise_faceyoga.ui.home.HomeFragment
import com.bytezaptech.jawlineexercise_faceyoga.utils.BottomNavViewBehavior
import com.bytezaptech.jawlineexercise_faceyoga.utils.showSuccess
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    private var isBackPressed: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        viewModel = ViewModelProvider(this, MainViewModelFactory(this))[MainViewModel::class.java]

        setupViews()
    }

    private fun setupViews() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host) as NavHostFragment

        val navController = navHostFragment.navController

        binding.bottomNavView.setupWithNavController(navController)
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        val stackEntryCount =
            (supportFragmentManager.findFragmentById(R.id.nav_host) as NavHostFragment)
                .childFragmentManager.backStackEntryCount

        val currFragment =
            (supportFragmentManager.findFragmentById(R.id.nav_host) as NavHostFragment)
                .childFragmentManager.fragments[0]

        //Display quit dialog
        if (currFragment is ExerciseDoingFragment) {
            currFragment.quitDialog()
            return
        }

        if (stackEntryCount == 0) {
            when (isBackPressed) {
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
        } else {
            super.onBackPressed()
        }
    }

    override fun onResume() {
        super.onResume()

        val navHost = supportFragmentManager.findFragmentById(R.id.nav_host) as NavHostFragment
        navHost.navController.addOnDestinationChangedListener(listener)
    }

    override fun onPause() {
        super.onPause()

        val navHost = supportFragmentManager.findFragmentById(R.id.nav_host) as NavHostFragment
        navHost.navController.removeOnDestinationChangedListener(listener)
    }

    private val listener = NavController.OnDestinationChangedListener { _, destination, _ ->
        when (destination.id) {
            R.id.home -> {
                lifecycleScope.launch {
                    //Because when come from exercise doing it not recreated and refresh exercise.
                    val navHost = supportFragmentManager.findFragmentById(R.id.nav_host) as NavHostFragment
                    val prevFrag = navHost.childFragmentManager.fragments[0]

                    delay(500)
                    val currFrag = navHost.childFragmentManager.fragments[0]

                    if (currFrag is HomeFragment && prevFrag is ExerciseDoingFragment)
                        currFrag.viewModel.addExerciseChallenges()
                }
            }
        }
    }
}