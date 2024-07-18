package com.bytezaptech.jawlineexercise_faceyoga.ui.history

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.bytezaptech.jawlineexercise_faceyoga.R
import com.bytezaptech.jawlineexercise_faceyoga.data.local.entities.GrowthEntity
import com.bytezaptech.jawlineexercise_faceyoga.data.repositories.MainRepository
import com.bytezaptech.jawlineexercise_faceyoga.databinding.FragmentHistoryBinding
import com.bytezaptech.jawlineexercise_faceyoga.utils.MyApplication
import com.bytezaptech.jawlineexercise_faceyoga.utils.Success
import java.sql.Date
import java.util.Calendar
import javax.inject.Inject

class HistoryFragment : Fragment() {
    private lateinit var binding: FragmentHistoryBinding
    private lateinit var viewModel: HistoryViewModel
    @Inject
    lateinit var mainRepo: MainRepository

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as MyApplication).appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_history, container, false)
        viewModel = ViewModelProvider(this@HistoryFragment, HistoryViewModelFactory(mainRepo))[HistoryViewModel::class.java]

        binding.lifecycleOwner = this

        viewModel.getHistory()
        setObservers()
        return binding.root
    }

    private fun setObservers() {
        viewModel.historyLD.observe(viewLifecycleOwner) {
            if(it is Success<*>) {
                val list = it.data as List<GrowthEntity>

                val cal = Calendar.getInstance()
                cal.time = Date(list[0].timestamp!!)
                binding.calendarView.setMinimumDate(cal)
                cal.time = Date(list[list.size - 1].timestamp!!)
                binding.calendarView.setMaximumDate(cal)
            }
        }
    }
}