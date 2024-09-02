package com.bytezaptech.jawlineexercise_faceyoga.ui.settings

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.bytezaptech.jawlineexercise_faceyoga.R
import com.bytezaptech.jawlineexercise_faceyoga.adapters.LanguageAdapter
import com.bytezaptech.jawlineexercise_faceyoga.data.local.SharedPref
import com.bytezaptech.jawlineexercise_faceyoga.data.local.entities.LanguageEntity
import com.bytezaptech.jawlineexercise_faceyoga.data.repositories.MainRepository
import com.bytezaptech.jawlineexercise_faceyoga.databinding.FragmentLanguageChangeDialogBinding
import com.bytezaptech.jawlineexercise_faceyoga.ui.splash.SplashActivity
import com.bytezaptech.jawlineexercise_faceyoga.utils.MyApplication
import com.bytezaptech.jawlineexercise_faceyoga.utils.Success
import javax.inject.Inject

class LanguageChangeDialogFragment : DialogFragment() {
    private lateinit var binding: FragmentLanguageChangeDialogBinding
    lateinit var viewModel: SettingsViewModel
    @Inject
    lateinit var mainRepo: MainRepository
    @Inject
    lateinit var sharedPref: SharedPref

    override fun onAttach(context: Context) {
        (requireActivity().application as MyApplication).appComponent.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_language_change_dialog, container, false)
        viewModel = ViewModelProvider(this, SettingsViewModelFactory(mainRepo))[SettingsViewModel::class.java]

        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        mainRepo.getLanguages()
        setObservers()
        return binding.root
    }

    private fun setObservers() {
        viewModel.languageLD.observe(viewLifecycleOwner) {
            when(it) {
                is Success<*> -> {
                    val list = it.data as List<LanguageEntity>
                    val adapter = LanguageAdapter((this), object: DiffUtil.ItemCallback<LanguageEntity>() {
                        override fun areContentsTheSame(
                            oldItem: LanguageEntity,
                            newItem: LanguageEntity
                        ): Boolean {
                            return oldItem.langCode == newItem.langCode
                        }

                        override fun areItemsTheSame(
                            oldItem: LanguageEntity,
                            newItem: LanguageEntity
                        ): Boolean {
                            return oldItem == newItem
                        }
                    })

                    binding.languageRecyclerView.adapter = adapter
                    binding.languageRecyclerView.layoutManager = LinearLayoutManager(requireContext())

                    adapter.submitList(list)
                }

                else -> {}
            }
        }

        viewModel.languageSelLD.observe(viewLifecycleOwner) {
            when(it) {
                is Success<*> -> {
                    val intent = Intent(requireActivity(), SplashActivity::class.java)
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                    requireActivity().startActivity(intent)
                }
                else -> {}
            }
        }
    }
}