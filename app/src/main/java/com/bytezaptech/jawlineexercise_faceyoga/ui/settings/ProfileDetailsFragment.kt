package com.bytezaptech.jawlineexercise_faceyoga.ui.settings

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bytezaptech.jawlineexercise_faceyoga.R
import com.bytezaptech.jawlineexercise_faceyoga.data.local.entities.UserEntity
import com.bytezaptech.jawlineexercise_faceyoga.data.repositories.MainRepository
import com.bytezaptech.jawlineexercise_faceyoga.databinding.FragmentProfileDetailsBinding
import com.bytezaptech.jawlineexercise_faceyoga.ui.main.MainActivity
import com.bytezaptech.jawlineexercise_faceyoga.ui.onboard_details.OnboardDetailsActivity
import com.bytezaptech.jawlineexercise_faceyoga.ui.splash.SplashActivity
import com.bytezaptech.jawlineexercise_faceyoga.utils.Error
import com.bytezaptech.jawlineexercise_faceyoga.utils.MyApplication
import com.bytezaptech.jawlineexercise_faceyoga.utils.Success
import com.bytezaptech.jawlineexercise_faceyoga.utils.checkInternet
import com.bytezaptech.jawlineexercise_faceyoga.utils.showError
import com.bytezaptech.jawlineexercise_faceyoga.utils.showSuccess
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import javax.inject.Inject

class ProfileDetailsFragment : Fragment() {
    private lateinit var binding: FragmentProfileDetailsBinding
    private lateinit var viewModel: SettingsViewModel

    @Inject
    lateinit var mainRepo: MainRepository

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity?.application as MyApplication).appComponent.inject(this)
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
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_profile_details, container, false)
        viewModel = ViewModelProvider(
            this,
            SettingsViewModelFactory(mainRepo)
        )[SettingsViewModel::class.java]

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        viewModel.getProfile()
        setObservers()
        setListeners()
        return binding.root
    }

    private fun setObservers() {
        viewModel.profileLD.observe(viewLifecycleOwner) {
            when (it) {
                is Success<*> -> {
                    val userProfile = it.data as UserEntity
                    binding.planTv.text = userProfile.plan
                    binding.nameTv.text = userProfile.name
                    binding.emailTv.text = userProfile.email

                    Glide.with(requireContext()).load(userProfile.profile)
                        .placeholder(R.drawable.user_profile).into(binding.profileIv)
                }

                is Error -> {}

                else -> {}
            }
        }

        viewModel.signOutLD.observe(viewLifecycleOwner) {
            when (it) {
                is Success<*> -> {
                    // Google sign out
                    val gsc = GoogleSignIn.getClient(requireContext(), GoogleSignInOptions.DEFAULT_SIGN_IN)
                    gsc.signOut().addOnCompleteListener {}
                    val intent = Intent(requireContext(), SplashActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                    startActivity(intent)

                    Toast(requireContext()).apply {
                        showSuccess(this, requireContext(), binding.root as ViewGroup, it.data?.toString()!!)
                    }
                }

                is Error -> {
                    Toast(requireContext()).apply {
                        showError(this, requireContext(), binding.root as ViewGroup, it.error)
                    }
                }

                else -> {}
            }
        }
    }

    private fun setListeners() {
        binding.ivUp.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.editTv.setOnClickListener {
            val intent = Intent(requireActivity(), OnboardDetailsActivity::class.java)
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            context?.startActivity(intent)
        }

        binding.signOutBtn.setOnClickListener {
            viewModel.signOutUser()
        }

        binding.delAccTv.setOnClickListener {
            if (checkInternet(requireActivity(), binding.root as ViewGroup))
                viewModel.deleteAccount()
        }
    }

}