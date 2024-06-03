package com.bytezaptech.jawlineexercise_faceyoga.adapters

import android.view.LayoutInflater
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bytezaptech.jawlineexercise_faceyoga.databinding.ExcerciseDetListItemBinding
import com.bytezaptech.jawlineexercise_faceyoga.models.EachDayExerciseModel

class EachDayExerciseAdapter(diffUtil: DiffUtil.ItemCallback<EachDayExerciseModel>): ListAdapter<EachDayExerciseModel, EachDayExerciseAdapter.ViewHolder>(diffUtil) {

    inner class ViewHolder(val binding: ExcerciseDetListItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(exercise: EachDayExerciseModel) {
            binding.excNameTv.text = exercise.title
            binding.timeTv.text = exercise.duration

            binding.lottieIv.setAnimation(exercise.img!!)
        }
    }

    override fun onCreateViewHolder(parent: android.view.ViewGroup, viewType: Int): ViewHolder {
        val binding = ExcerciseDetListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}