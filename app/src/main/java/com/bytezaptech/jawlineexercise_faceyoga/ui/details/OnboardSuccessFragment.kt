package com.bytezaptech.jawlineexercise_faceyoga.ui.details

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import com.bytezaptech.jawlineexercise_faceyoga.R
import com.bytezaptech.jawlineexercise_faceyoga.databinding.FragmentOnboardSuccessBinding
import com.bytezaptech.jawlineexercise_faceyoga.ui.main.MainActivity
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class OnboardSuccessFragment : Fragment() {
    private lateinit var binding: FragmentOnboardSuccessBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_onboard_success, container, false)

        binding.nextBtn.setOnClickListener {
            lifecycleScope.launch {
                delay(2000)
                val intent = Intent(requireActivity(), MainActivity::class.java)
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
                requireActivity().finish()
            }
        }
        return binding.root
    }

}