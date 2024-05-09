package com.bytezaptech.jawlineexercise_faceyoga.ui.auth

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.databinding.DataBindingUtil
import com.bytezaptech.jawlineexercise_faceyoga.R
import com.bytezaptech.jawlineexercise_faceyoga.data.local.SharedPref
import com.bytezaptech.jawlineexercise_faceyoga.databinding.AuthBottomSheetBinding
import com.bytezaptech.jawlineexercise_faceyoga.ui.details.OnboardDetailsActivity
import com.bytezaptech.jawlineexercise_faceyoga.ui.main.MainActivity
import com.bytezaptech.jawlineexercise_faceyoga.utils.Constants
import com.bytezaptech.jawlineexercise_faceyoga.utils.Error
import com.bytezaptech.jawlineexercise_faceyoga.utils.MyApplication
import com.bytezaptech.jawlineexercise_faceyoga.utils.Progress
import com.bytezaptech.jawlineexercise_faceyoga.utils.Success
import com.bytezaptech.jawlineexercise_faceyoga.utils.showError
import com.bytezaptech.jawlineexercise_faceyoga.utils.showSuccess
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.auth.GoogleAuthProvider
import javax.inject.Inject

class AuthBottomSheetFragment : BottomSheetDialogFragment() {
    lateinit var binding: AuthBottomSheetBinding
    @Inject
    lateinit var googleSignOption: GoogleSignInOptions
    @Inject
    lateinit var sharedPref: SharedPref
    lateinit var googleSignInClient: GoogleSignInClient
    lateinit var viewModel: LoginAndSignUpViewModel

    override fun onAttach(context: Context) {
        (requireActivity().application as MyApplication).appComponent.getAuthSubcomponent().create()
            .inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.auth_bottom_sheet, container, false)

        googleSignInClient = GoogleSignIn.getClient(requireActivity(), googleSignOption)
        viewModel = (activity as LoginAndSIgnUp).viewModel
        setObservers()
        setListeners()
        return binding.root
    }

    private fun setObservers() {
        viewModel.authLiveData.observe(requireActivity()) {
            if (it is Success<*>) {
                isCancelable = true
                binding.llSignBtn.isEnabled = false
                binding.btnLl.visibility = View.VISIBLE
                binding.pBarBtn.visibility = View.GONE
                if(sharedPref.getBoolean(Constants.isDetailFilled)) {
                    val intent = Intent(requireActivity(), MainActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    startActivity(intent)
                    requireActivity().finish()
                } else {
                    val intent = Intent(requireActivity(), OnboardDetailsActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    startActivity(intent)
                    requireActivity().finish()
                }
                Toast(requireContext()).apply {
                    this.showSuccess(this, requireContext(), binding.root as ViewGroup, it.data.toString())
                }
            } else if (it is Error) {
                isCancelable = true
                binding.llSignBtn.isEnabled = true
                binding.btnLl.visibility = View.VISIBLE
                binding.pBarBtn.visibility = View.GONE
                Toast(requireContext()).apply {
                    this.showError(this, requireContext(), binding.root as ViewGroup, it.error)
                }
            } else if (it is Progress) {
                isCancelable = false
                binding.llSignBtn.isEnabled = false
                binding.btnLl.visibility = View.GONE
                binding.pBarBtn.visibility = View.VISIBLE
            }
        }
    }

    private fun setListeners() {
        binding.llSignBtn.setOnTouchListener(object : View.OnTouchListener {
            override fun onTouch(v: View, motionEvent: MotionEvent): Boolean {
                when (motionEvent.action) {
                    MotionEvent.ACTION_DOWN -> {
                        (requireActivity().application as MyApplication).scaleView(
                            binding.llSignBtn,
                            true
                        )
                    }

                    MotionEvent.ACTION_UP -> {
                        (requireActivity().application as MyApplication).scaleView(
                            binding.llSignBtn,
                            false
                        )
                        activityResultContract.launch(googleSignInClient.signInIntent)
                    }
                }

                return true
            }
        })
    }

    val activityResultContract =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            val googleSignInAccount = GoogleSignIn.getSignedInAccountFromIntent(it.data)
            if (googleSignInAccount.isSuccessful) {
                //google sign in successfully initialize
                val googleSignInAccount = googleSignInAccount.getResult(ApiException::class.java)
                if (googleSignInAccount != null) {
                    val authCred =
                        GoogleAuthProvider.getCredential(googleSignInAccount.idToken, null)
                    val name = googleSignInAccount.givenName ?: "Guest"
                    val email = googleSignInAccount.email ?: ""
                    val profileImg = googleSignInAccount.photoUrl.toString()
                    viewModel.signInOrSignUp(authCred, name, email, profileImg)
                }
            }
        }

}