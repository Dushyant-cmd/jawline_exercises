package com.bytezaptech.jawlineexercise_faceyoga.adapters

import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bytezaptech.jawlineexercise_faceyoga.R
import com.bytezaptech.jawlineexercise_faceyoga.databinding.ExerciseListItemBinding
import com.bytezaptech.jawlineexercise_faceyoga.models.ExerciseListModel
import com.bytezaptech.jawlineexercise_faceyoga.ui.home.HomeViewModel
import com.bytezaptech.jawlineexercise_faceyoga.utils.MyApplication

class ExerciseListAdapter(
    val application: MyApplication,
    val viewModel: HomeViewModel,
    diffCallback: DiffUtil.ItemCallback<ExerciseListModel>
) : ListAdapter<ExerciseListModel, ExerciseListAdapter.ExerciseViewHolder>(diffCallback) {

    inner class ExerciseViewHolder(val binding: ExerciseListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: ExerciseListModel) {
            when (data.isFinished) {
                true -> {
                    binding.openRl.visibility = View.VISIBLE
                    binding.lockRl.visibility = View.GONE

                    binding.dayOpenTv.text = data.name
                }

                false -> {
                    binding.openRl.visibility = View.GONE
                    binding.lockRl.visibility = View.VISIBLE

                    binding.dayLockTv.text = data.name
                }
            }

            when (data.exerciseChallenge.totalDays) {
                30 -> {
                    binding.openRl.backgroundTintList =
                        ContextCompat.getColorStateList(binding.root.context, R.color.blue)
                    binding.tvStart.setTextColor(
                        ResourcesCompat.getColor(
                            binding.root.resources,
                            R.color.blue,
                            null
                        )
                    )
                    binding.ivStart.imageTintList =
                        ContextCompat.getColorStateList(binding.root.context, R.color.blue)
                }

                60 -> {
                    binding.openRl.backgroundTintList =
                        ContextCompat.getColorStateList(binding.root.context, R.color.light_green)
                    binding.tvStart.setTextColor(
                        ResourcesCompat.getColor(
                            binding.root.resources,
                            R.color.light_green,
                            null
                        )
                    )
                    binding.ivStart.imageTintList =
                        ContextCompat.getColorStateList(binding.root.context, R.color.light_green)
                }

                120 -> {
                    binding.openRl.backgroundTintList =
                        ContextCompat.getColorStateList(binding.root.context, R.color.dark_red)
                    binding.tvStart.setTextColor(
                        ResourcesCompat.getColor(
                            binding.root.resources,
                            R.color.dark_red,
                            null
                        )
                    )
                    binding.ivStart.imageTintList =
                        ContextCompat.getColorStateList(binding.root.context, R.color.dark_red)
                }
            }

            binding.root.setOnTouchListener(object : View.OnTouchListener {
                override fun onTouch(v: View?, motionEvent: MotionEvent?): Boolean {
                    if (motionEvent != null) {
                        when (motionEvent.action) {
                            MotionEvent.ACTION_DOWN -> {
                                application.scaleView(
                                    binding.root,
                                    true
                                )
                            }

                            MotionEvent.ACTION_UP -> {
                                application.scaleView(
                                    binding.root,
                                    false
                                )
                                viewModel.openExerciseDetails(data)
                            }

                            MotionEvent.ACTION_CANCEL -> {
                                application.scaleView(
                                    binding.root,
                                    false
                                )
                            }
                        }
                    }

                    return true
                }
            })
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseViewHolder {
        val binding: ExerciseListItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.exercise_list_item,
            parent,
            false
        )
        return ExerciseViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ExerciseViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}