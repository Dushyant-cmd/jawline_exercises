package com.bytezaptech.jawlineexercise_faceyoga.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bytezaptech.jawlineexercise_faceyoga.R
import com.bytezaptech.jawlineexercise_faceyoga.data.local.entities.ExerciseListModel
import com.bytezaptech.jawlineexercise_faceyoga.databinding.ExerciseListItemBinding
import com.bytezaptech.jawlineexercise_faceyoga.ui.home.HomeViewModel

class ExerciseListAdapter(val viewModel: HomeViewModel, diffCallback: DiffUtil.ItemCallback<ExerciseListModel>): ListAdapter<ExerciseListModel, ExerciseListAdapter.ExerciseViewHolder>(diffCallback) {

    inner class ExerciseViewHolder(val binding: ExerciseListItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(data: ExerciseListModel) {
            when(data.isFinished) {
                true -> {
                    binding.openRl.visibility = View.VISIBLE
                    binding.lockRl.visibility = View.GONE
                }
                false -> {
                    binding.openRl.visibility = View.GONE
                    binding.lockRl.visibility = View.VISIBLE
                }
            }

            binding.root.setOnClickListener {
                viewModel.openExerciseDetails(data)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseViewHolder {
        val binding: ExerciseListItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.exercise_list_item, parent, false)
        return ExerciseViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ExerciseViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}