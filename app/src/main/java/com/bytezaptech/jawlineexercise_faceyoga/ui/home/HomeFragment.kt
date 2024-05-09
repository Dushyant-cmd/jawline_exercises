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
import com.bytezaptech.jawlineexercise_faceyoga.data.repositories.MainRepository
import com.bytezaptech.jawlineexercise_faceyoga.databinding.FragmentHomeBinding
import com.bytezaptech.jawlineexercise_faceyoga.utils.MyApplication
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        
        viewModel = ViewModelProvider(requireActivity(), HomeViewModelFactory(mainRepo))[HomeViewModel::class.java]
        val list = listOf(ThirtyDaysFragment(), SixtyDaysFragment(), OneTwentyDaysFragment())
        val pagerAdapter = ViewPagerAdapter(list, requireActivity())
        binding.viewPager2.setAdapter(pagerAdapter)
    }
}