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
import com.bytezaptech.jawlineexercise_faceyoga.data.repositories.MainRepository
import com.bytezaptech.jawlineexercise_faceyoga.databinding.FragmentHomeBinding
import com.bytezaptech.jawlineexercise_faceyoga.utils.Error
import com.bytezaptech.jawlineexercise_faceyoga.utils.MyApplication
import com.bytezaptech.jawlineexercise_faceyoga.utils.Progress
import com.bytezaptech.jawlineexercise_faceyoga.utils.Success
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
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

        //add exercise call from main activity destination changed listener.
        viewModel.addExerciseChallenges()
        setObservers()
        return binding.root
    }

    private fun setObservers() {
        viewModel.exerciseChallenge.observe(viewLifecycleOwner) {
            when (it) {
                is Success<*> -> {
                    lifecycleScope.launch(Dispatchers.Main) {
                        val pagerAdapter =
                            ViewPagerAdapter(it.data as List<Fragment>, requireActivity())
                        binding.viewPager2.setAdapter(pagerAdapter)

                        binding.progressBar.visibility = View.GONE
                        binding.viewPager2.visibility = View.VISIBLE
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