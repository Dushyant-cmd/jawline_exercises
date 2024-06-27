package com.bytezaptech.jawlineexercise_faceyoga.ui.home

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.bytezaptech.jawlineexercise_faceyoga.adapters.ViewPagerAdapter
import com.bytezaptech.jawlineexercise_faceyoga.data.local.entities.ExerciseChallenge
import com.bytezaptech.jawlineexercise_faceyoga.data.repositories.MainRepository
import com.bytezaptech.jawlineexercise_faceyoga.databinding.FragmentHomeBinding
import com.bytezaptech.jawlineexercise_faceyoga.models.ExerciseListModel
import com.bytezaptech.jawlineexercise_faceyoga.utils.Error
import com.bytezaptech.jawlineexercise_faceyoga.utils.MyApplication
import com.bytezaptech.jawlineexercise_faceyoga.utils.Progress
import com.bytezaptech.jawlineexercise_faceyoga.utils.Success
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    @Inject
    lateinit var mainRepo: MainRepository
    lateinit var viewModel: HomeViewModel

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as MyApplication).appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(
            requireActivity(),
            HomeViewModelFactory(mainRepo)
        )[HomeViewModel::class.java]


        // ADD ALL CHALLENGES IN EXERCISE CHALLENGE TABLE.
        viewModel.addExerciseChallenges()
        setObservers()
        return binding.root
    }

    private fun setObservers() {
        viewModel.exerciseChallenge.observe(viewLifecycleOwner) {
            when (it) {
                is Success<*> -> {
                    lifecycleScope.launch(Dispatchers.IO) {
                        val listOfExercises = it.data as List<ExerciseChallenge>
                        val list = ArrayList<Fragment>()
                        for (i in listOfExercises.indices) {
                            val listOfDays = ArrayList<ExerciseListModel>()

                            for (j in 1..listOfExercises[i].totalDays!!) {
                                var isFinished = false
                                if(j <= listOfExercises[i].daysCompleted!!)
                                    isFinished = true

                                listOfDays.add(ExerciseListModel("Day $j", isFinished, listOfExercises[i]))
                            }

                            val bundle = Bundle()
                            bundle.putSerializable("list", listOfDays)

                            when (listOfExercises[i].totalDays) {
                                30 -> {
                                    val frag = ThirtyDaysFragment()
                                    frag.arguments = bundle
                                    list.add(frag)
                                }

//                            60 -> {
//                                val frag = SixtyDaysFragment()
//                                frag.arguments = bundle
//                                list.add(frag)
//                            }
//
//                            120 -> {
//                                val frag = OneTwentyDaysFragment()
//                                frag.arguments = bundle
//                                list.add(frag)
//                            }
                            }
                        }

                        withContext(Dispatchers.Main) {
                            val pagerAdapter = ViewPagerAdapter(list, requireActivity())
                            binding.viewPager2.setAdapter(pagerAdapter)

                            binding.progressBar.visibility = View.GONE
                            binding.viewPager2.visibility = View.VISIBLE
                        }
                    }
                }

                is Error -> {}

                is Progress -> {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.viewPager2.visibility = View.GONE
                }
            }
        }
    }
}