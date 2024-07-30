package com.bytezaptech.jawlineexercise_faceyoga.ui.history

import android.content.Context
import android.os.Bundle
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.bytezaptech.jawlineexercise_faceyoga.R
import com.bytezaptech.jawlineexercise_faceyoga.adapters.ArticleAdapter
import com.bytezaptech.jawlineexercise_faceyoga.data.local.entities.ArticleEntity
import com.bytezaptech.jawlineexercise_faceyoga.data.local.entities.GrowthEntity
import com.bytezaptech.jawlineexercise_faceyoga.data.repositories.MainRepository
import com.bytezaptech.jawlineexercise_faceyoga.databinding.FragmentHistoryBinding
import com.bytezaptech.jawlineexercise_faceyoga.utils.MyApplication
import com.bytezaptech.jawlineexercise_faceyoga.utils.Success
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.MaterialCalendarView.SELECTION_MODE_MULTIPLE
import com.prolificinteractive.materialcalendarview.MaterialCalendarView.SELECTION_MODE_NONE
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

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) {v, insets ->
            val systemBar = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBar.left, systemBar.top, systemBar.right, systemBar.bottom)
            insets
        }

        viewModel.getHistory()
        viewModel.getArticles()
        setObservers()
        return binding.root
    }

    private fun setObservers() {
        viewModel.historyLD.observe(viewLifecycleOwner) {
            if(it is Success<*>) {
                val list = it.data as List<GrowthEntity>
                binding.calendarView.selectionMode = SELECTION_MODE_MULTIPLE
                for(data in list) {
                    val calendar = Calendar.getInstance()
                    calendar.time = Date(data.timestamp!!)
                    val cal = CalendarDay.from(calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH))
                    binding.calendarView.setDateSelected(cal, true)
                }

                binding.calendarView.setSelectionMode(SELECTION_MODE_NONE); // Removes onClick functionality
            }
        }

        viewModel.articleLD.observe(viewLifecycleOwner) {
            if(it is Success<*>) {
                val list = it.data as List<ArticleEntity>
                val adapter = ArticleAdapter(requireActivity(), object: DiffUtil.ItemCallback<ArticleEntity>() {
                    override fun areContentsTheSame(
                        oldItem: ArticleEntity,
                        newItem: ArticleEntity
                    ): Boolean {
                        return oldItem.id == newItem.id
                    }
                    override fun areItemsTheSame(
                        oldItem: ArticleEntity,
                        newItem: ArticleEntity
                    ): Boolean {
                        return oldItem == newItem
                    }
                })

                binding.recyclerView.adapter = adapter
                binding.recyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

                adapter.submitList(list)
            }
        }
    }
}