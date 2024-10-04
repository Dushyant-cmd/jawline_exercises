package com.bytezaptech.jawlineexercise_faceyoga.ui.home

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.Observable
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bytezaptech.jawlineexercise_faceyoga.R
import com.bytezaptech.jawlineexercise_faceyoga.adapters.ExerciseListAdapter
import com.bytezaptech.jawlineexercise_faceyoga.data.local.entities.UserEntity
import com.bytezaptech.jawlineexercise_faceyoga.data.repositories.MainRepository
import com.bytezaptech.jawlineexercise_faceyoga.databinding.FragmentThirtyDaysBinding
import com.bytezaptech.jawlineexercise_faceyoga.models.ExerciseListModel
import com.bytezaptech.jawlineexercise_faceyoga.ui.main.MainActivity
import com.bytezaptech.jawlineexercise_faceyoga.utils.MyApplication
import com.bytezaptech.jawlineexercise_faceyoga.utils.Response
import com.bytezaptech.jawlineexercise_faceyoga.utils.Success
import com.bytezaptech.jawlineexercise_faceyoga.utils.findNavControllerSafety
import com.bytezaptech.jawlineexercise_faceyoga.utils.showMessageDialog
import javax.inject.Inject

class ThirtyDaysFragment : Fragment() {
    private lateinit var binding: FragmentThirtyDaysBinding
    private lateinit var viewModel: HomeViewModel
    @Inject
    lateinit var mainRepository: MainRepository
    lateinit var userProfile: UserEntity
    lateinit var list: List<ExerciseListModel>

    override fun onAttach(context: Context) {
        (requireActivity().application as MyApplication).appComponent.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_thirty_days, container, false)
        viewModel = ViewModelProvider(this, HomeViewModelFactory(mainRepository))[HomeViewModel::class.java]
        binding.viewModel = viewModel
        binding.lifecycleOwner = this


        viewModel.getUserProfile()
        setListeners()
        setupViews()
        setObservers()
        return binding.root
    }

    private fun setListeners() {
        binding.ivProfile.setOnClickListener {
            val action = HomeFragmentDirections.homeToSettings()
            findNavControllerSafety(R.id.home)?.navigate(action)
        }
    }

    private fun setupViews() {
        list = arguments?.getSerializable("list") as List<ExerciseListModel>

        val exChallenge = list[0].exerciseChallenge
        val daysCompleted = if(exChallenge?.daysCompleted == exChallenge?.totalDays) exChallenge?.daysCompleted
        else exChallenge?.daysCompleted?.dec()

        binding.tvResultHead.text = "${daysCompleted}/${exChallenge?.totalDays} Finished"

        val adapter = ExerciseListAdapter((requireActivity() as MainActivity).application as MyApplication, viewModel, object: DiffUtil.ItemCallback<ExerciseListModel>(){
            override fun areItemsTheSame(oldItem: ExerciseListModel, newItem: ExerciseListModel): Boolean {
                return oldItem.name == newItem.name
            }
            override fun areContentsTheSame(oldItem: ExerciseListModel, newItem: ExerciseListModel): Boolean {
                return oldItem == newItem
            }
        })

        binding.thirtyDaysRv.layoutManager = LinearLayoutManager(requireContext())
        binding.thirtyDaysRv.adapter = adapter

        adapter.submitList(list)
    }

    private fun setObservers() {
        viewModel.exerciseDetails.observe(viewLifecycleOwner) {
            when(it) {
                is Success<*> -> {
                    val exerciseListModel = (it.data as ExerciseListModel)
                    if(exerciseListModel.isFinished) {
                        val day = exerciseListModel.day
                        val action = HomeFragmentDirections.homeToExercise(exerciseListModel.exerciseChallenge!!, day)
                        findNavControllerSafety(R.id.home)?.navigate(action)
                    } else
                        showMessageDialog(requireContext(), "No cheating", "complete previous days to unlock this one", "OK")
                }
                else -> {}
            }
        }

        viewModel.userProfileData.observe(viewLifecycleOwner
        ) { value ->
            when (value) {
                is Success<*> -> {
                    userProfile = value.data as UserEntity
                    Glide.with(requireContext()).load(userProfile.profile).placeholder(R.drawable.user_profile).into(binding.ivProfile)
                }

                else -> {}
            }
        }
    }
}