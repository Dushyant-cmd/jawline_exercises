package com.bytezaptech.jawlineexercise_faceyoga.ui.growth

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.bytezaptech.jawlineexercise_faceyoga.R
import com.bytezaptech.jawlineexercise_faceyoga.data.repositories.MainRepository
import com.bytezaptech.jawlineexercise_faceyoga.databinding.FragmentGrowthBinding
import com.bytezaptech.jawlineexercise_faceyoga.utils.MyApplication
import javax.inject.Inject

class GrowthFragment : Fragment() {
    private val TAG: String? = "GrowthFragment.java"
    private lateinit var binding: FragmentGrowthBinding
    private lateinit var viewModel: GrowthViewModel
    @Inject
    lateinit var mainRepo: MainRepository

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as MyApplication).appComponent.inject(this)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentGrowthBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this, GrowthViewModelFactory(mainRepo))[GrowthViewModel::class.java]

        setListeners()
        return binding.root
    }

    private fun setListeners() {
        binding.root.setOnTouchListener({view, event ->
            when(event.action) {
                MotionEvent.ACTION_MOVE -> {
                    Log.d(TAG, "setListeners: move")
                }
            }
            true
        })
    }

}