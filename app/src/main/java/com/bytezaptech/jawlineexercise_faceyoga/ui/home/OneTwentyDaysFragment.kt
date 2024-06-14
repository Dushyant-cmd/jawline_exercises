package com.bytezaptech.jawlineexercise_faceyoga.ui.home

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import com.bytezaptech.jawlineexercise_faceyoga.databinding.FragmentOneTwentyDaysBinding
import com.bytezaptech.jawlineexercise_faceyoga.models.ExerciseListModel
import com.bytezaptech.jawlineexercise_faceyoga.utils.MyApplication
import com.bytezaptech.jawlineexercise_faceyoga.utils.Response
import com.bytezaptech.jawlineexercise_faceyoga.utils.Success
import com.bytezaptech.jawlineexercise_faceyoga.utils.showMessageDialog
import javax.inject.Inject

class OneTwentyDaysFragment : Fragment() {
    private lateinit var binding: FragmentOneTwentyDaysBinding
    private lateinit var viewModel: HomeViewModel
    @Inject
    lateinit var mainRepo: MainRepository
    private lateinit var userProfile: UserEntity
    private lateinit var list: List<ExerciseListModel>

    override fun onAttach(context: Context) {
        (requireActivity().application as MyApplication).appComponent.inject(this)
        super.onAttach(context)

        list = arguments?.getSerializable("list") as List<ExerciseListModel>
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOneTwentyDaysBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this, HomeViewModelFactory(mainRepo))[HomeViewModel::class.java]
        binding.lifecycleOwner = this

        viewModel.getUserProfile()

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
                        val day = exerciseListModel.exerciseChallenge.daysCompleted ?: 0
                        bundle.putInt("day", day)
                        bundle.putParcelable("exerciseChallenge", exerciseListModel.exerciseChallenge)
                        findNavController().navigate(R.id.home_to_exercise, bundle)
                    } else
                        showMessageDialog(requireContext(), "No cheating", "complete previous days to unlock this one", "OK")
                }
                else -> {}
            }
        }

        viewModel.userProfileData.observe(viewLifecycleOwner, object : Observer<Response> {
            override fun onChanged(value: Response) {
                when(value) {
                    is Success<*> -> {
                        userProfile = value.data as UserEntity
                        Glide.with(context!!).load(userProfile.profile).placeholder(R.drawable.user_profile).into(binding.ivProfile)
                    }

                    else -> {}
                }
            }
        })
    }

    private fun setupViews() {
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
    }

}