package com.bytezaptech.jawlineexercise_faceyoga.ui.home

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.bytezaptech.jawlineexercise_faceyoga.R
import com.bytezaptech.jawlineexercise_faceyoga.adapters.ViewPagerAdapter
import com.bytezaptech.jawlineexercise_faceyoga.data.local.entities.ExerciseChallenge
import com.bytezaptech.jawlineexercise_faceyoga.data.repositories.MainRepository
import com.bytezaptech.jawlineexercise_faceyoga.databinding.FragmentHomeBinding
import com.bytezaptech.jawlineexercise_faceyoga.models.ExerciseListModel
import com.bytezaptech.jawlineexercise_faceyoga.utils.Error
import com.bytezaptech.jawlineexercise_faceyoga.utils.MyApplication
import com.bytezaptech.jawlineexercise_faceyoga.utils.Progress
import com.bytezaptech.jawlineexercise_faceyoga.utils.Response
import com.bytezaptech.jawlineexercise_faceyoga.utils.Success
import javax.inject.Inject

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    @Inject
    lateinit var mainRepo: MainRepository
    private lateinit var viewModel: HomeViewModel

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as MyApplication).appComponent.inject(this)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        return binding.root
    }

    private fun setObservers() {
        viewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        viewModel = ViewModelProvider(requireActivity(), HomeViewModelFactory(mainRepo))[HomeViewModel::class.java]
        setObservers()

        // ADD ALL CHALLENGES IN EXERCISE CHALLENGE TABLE.
        viewModel.addExerciseChallenges()

        viewModel.exerciseChallenge.observe(viewLifecycleOwner) {
            when(it) {
                is Success<*> -> {
//                    val listOfExercises = it.data as List<ExerciseChallenge>
//                    val list = ArrayList<Fragment>()
//                    for(i in 0..listOfExercises.size) {
//                        val listOfDays = ArrayList<ExerciseListModel>()
//                        for(i in 1..listOfExercises[i].totalDays!!) {
//                            listOfDays.add(ExerciseListModel("Day $i", false))
//                        }
//                    }
//                    val pagerAdapter = ViewPagerAdapter(list, requireActivity())
//                    binding.viewPager2.setAdapter(pagerAdapter)
                }
                is Error -> {
                }
                is Progress -> {}
            }
        }
    }
}