package com.bytezaptech.jawlineexercise_faceyoga.ui.growth

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.bytezaptech.jawlineexercise_faceyoga.R
import com.bytezaptech.jawlineexercise_faceyoga.adapters.GrowthListAdapter
import com.bytezaptech.jawlineexercise_faceyoga.data.local.entities.GrowthEntity
import com.bytezaptech.jawlineexercise_faceyoga.data.repositories.MainRepository
import com.bytezaptech.jawlineexercise_faceyoga.databinding.FragmentGrowthBinding
import com.bytezaptech.jawlineexercise_faceyoga.utils.Error
import com.bytezaptech.jawlineexercise_faceyoga.utils.MyApplication
import com.bytezaptech.jawlineexercise_faceyoga.utils.Success
import org.eazegraph.lib.models.PieModel
import javax.inject.Inject

class GrowthFragment : Fragment() {
    private val TAG: String? = "GrowthFragment.java"
    private lateinit var binding: FragmentGrowthBinding
    private lateinit var viewModel: GrowthViewModel

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
        binding = FragmentGrowthBinding.inflate(inflater, container, false)
        viewModel =
            ViewModelProvider(this, GrowthViewModelFactory(mainRepo))[GrowthViewModel::class.java]

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        viewModel.getGrowthListWithImage()
        viewModel.getGrowthListWithoutImage()
        setObservers()
        return binding.root
    }

    private fun setObservers() {
        viewModel.growthListLD.observe(viewLifecycleOwner) {
            if(it is Success<*>) {
                val adapter = GrowthListAdapter(
                    requireActivity(),
                    object : DiffUtil.ItemCallback<GrowthEntity>() {
                        override fun areItemsTheSame(
                            oldItem: GrowthEntity,
                            newItem: GrowthEntity
                        ): Boolean {
                            return oldItem.id == newItem.id
                        }

                        override fun areContentsTheSame(
                            oldItem: GrowthEntity,
                            newItem: GrowthEntity
                        ): Boolean {
                            return oldItem == newItem
                        }
                    })

                binding.recyclerView.adapter = adapter
                binding.recyclerView.layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

                val list = (it.data as List<GrowthEntity>)
                adapter.submitList(list)
            }
        }

        viewModel.growthListAllLD.observe(viewLifecycleOwner) {
            if (it is Success<*>) {
                val data = it.data as GrowthEntity

                binding.piechart2.addPieSlice(
                    PieModel(
                        "Days Completed",
                        data.day?.toFloat() ?: 0f,
                        ResourcesCompat.getColor(
                            requireActivity().resources,
                            R.color.color_primary,
                            requireActivity().theme
                        )
                    )
                )
            } else if(it is Error) {
                binding.piechart2.addPieSlice(
                    PieModel(
                        "Days Completed",
                        0f,
                        ResourcesCompat.getColor(
                            requireActivity().resources,
                            R.color.color_primary,
                            requireActivity().theme
                        )
                    )
                )
            }

            binding.piechart1.addPieSlice(
                PieModel(
                    "Total Days",
                    30f,
                    ResourcesCompat.getColor(
                        requireActivity().resources,
                        R.color.light_grey,
                        requireActivity().theme
                    )
                )
            )

            binding.piechart1.startAnimation()
            binding.piechart2.startAnimation()
        }
    }

}