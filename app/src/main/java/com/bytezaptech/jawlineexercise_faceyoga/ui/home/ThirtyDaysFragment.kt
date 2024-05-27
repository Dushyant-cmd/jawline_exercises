package com.bytezaptech.jawlineexercise_faceyoga.ui.home

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
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
import com.bytezaptech.jawlineexercise_faceyoga.utils.MyApplication
import com.bytezaptech.jawlineexercise_faceyoga.utils.Success
import javax.inject.Inject

class ThirtyDaysFragment : Fragment() {
    private lateinit var binding: FragmentThirtyDaysBinding
    private lateinit var viewModel: HomeViewModel
    @Inject
    lateinit var mainRepository: MainRepository
    lateinit var userProfile: UserEntity

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

        val list = arguments?.getSerializable("list") as List<ExerciseListModel>

        val adapter = ExerciseListAdapter(viewModel, object: DiffUtil.ItemCallback<ExerciseListModel>(){
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

        setupViews()
        setObservers()
        return binding.root
    }

    private fun setObservers() {
        viewModel.exerciseDetails.observe(viewLifecycleOwner) {
            when(it) {
                is Success<*> -> {
                    val exerciseListModel = (it.data as ExerciseListModel)
                    if(exerciseListModel.isFinished) {
                        val bundle = Bundle()
                        val day = exerciseListModel.exerciseChallenge.daysCompleted?.plus(1) ?: 0
                        bundle.putInt("day", day)
                        findNavController().navigate(R.id.home_to_exercise, bundle)
                    }
                    else
                        showMessage(context, "No cheating", "complete previous days to unlock this one", "OK")
                }
                else -> {}
            }
        }
    }

    private fun setupViews() {
        viewModel.getUserProfile()

        when(val response = viewModel.userProfileData) {
            is Success<*> -> {
                userProfile = response.data as UserEntity
                Glide.with(this).load(userProfile.profile).into(binding.ivProfile)
            }
            else -> {}
        }
    }

}