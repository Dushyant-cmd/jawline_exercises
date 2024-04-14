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
import com.bytezaptech.jawlineexercise_faceyoga.databinding.AuthBottomSheetBinding
import com.bytezaptech.jawlineexercise_faceyoga.ui.main.MainActivity
import com.bytezaptech.jawlineexercise_faceyoga.utils.Error
import com.bytezaptech.jawlineexercise_faceyoga.utils.MyApplication
import com.bytezaptech.jawlineexercise_faceyoga.utils.Progress
import com.bytezaptech.jawlineexercise_faceyoga.utils.Success
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
    lateinit var googleSignInClient: GoogleSignInClient
    lateinit var viewModel: LoginAndSignUpViewModel

    override fun onAttach(context: Context) {
        (requireActivity().application as MyApplication).appComponent.getAuthComponent().create()
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
                binding.llSignBtn.isClickable = false
                binding.btnLl.visibility = View.VISIBLE
                binding.pBarBtn.visibility = View.GONE
                startActivity(Intent(requireContext(), MainActivity::class.java))
            } else if (it is Error) {
                isCancelable = true
                binding.llSignBtn.isClickable = true
                binding.btnLl.visibility = View.VISIBLE
                binding.pBarBtn.visibility = View.GONE
                Toast.makeText(requireContext(), it.error, Toast.LENGTH_LONG).show()
            } else if (it is Progress) {
                isCancelable = false
                binding.llSignBtn.isClickable = false
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
                    val authCred = GoogleAuthProvider.getCredential(googleSignInAccount.idToken, null)
                    val name = googleSignInAccount.givenName ?: "Guest"
                    val email = googleSignInAccount.email
                    viewModel.signInOrSignUp(authCred, name, email!!)
                }
            }
        }

}