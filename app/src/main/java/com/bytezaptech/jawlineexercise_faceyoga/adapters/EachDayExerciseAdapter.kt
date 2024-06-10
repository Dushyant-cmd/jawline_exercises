package com.bytezaptech.jawlineexercise_faceyoga.adapters

import android.content.Context
import android.view.LayoutInflater
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bytezaptech.jawlineexercise_faceyoga.R
import com.bytezaptech.jawlineexercise_faceyoga.databinding.ExcerciseDetListItemBinding
import com.bytezaptech.jawlineexercise_faceyoga.models.EachDayExerciseModel
import com.google.android.play.integrity.internal.i

class EachDayExerciseAdapter(val context: Context,
    diffUtil: DiffUtil.ItemCallback<EachDayExerciseModel>
) : ListAdapter<EachDayExerciseModel, EachDayExerciseAdapter.ViewHolder>(diffUtil) {

    var firstPhase: Int = 0; var secPhase: Int = 0

    override fun onCurrentListChanged(
        previousList: MutableList<EachDayExerciseModel>,
        currentList: MutableList<EachDayExerciseModel>
    ) {
        super.onCurrentListChanged(previousList, currentList)
        firstPhase = currentList.size / 2
        secPhase = (currentList.size - firstPhase) / 2
    }

    inner class ViewHolder(val binding: ExcerciseDetListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(exercise: EachDayExerciseModel, pos: Int) {
            binding.excNameTv.text = exercise.title
            binding.timeTv.text = exercise.duration + " sec"

            binding.lottieIv.setAnimation(exercise.img!!)

            if(pos < firstPhase) {
                binding.lottieLy.backgroundTintList = context.getColorStateList(R.color.light_pink)
                binding.deviderView.backgroundTintList = context.getColorStateList(R.color.dark_red)
            } else if(pos < (firstPhase + secPhase)) {
                binding.lottieLy.backgroundTintList = context.getColorStateList(R.color.pastel_tint)
                binding.deviderView.backgroundTintList = context.getColorStateList(R.color.pastel_tint)
            } else {
                binding.lottieLy.backgroundTintList = context.getColorStateList(R.color.violet_light)
                binding.deviderView.backgroundTintList = context.getColorStateList(R.color.color_primary)
            }
        }
    }

    override fun onCreateViewHolder(parent: android.view.ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ExcerciseDetListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), position)
    }
}