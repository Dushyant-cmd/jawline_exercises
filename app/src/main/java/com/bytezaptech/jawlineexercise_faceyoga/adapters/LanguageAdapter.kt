package com.bytezaptech.jawlineexercise_faceyoga.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bytezaptech.jawlineexercise_faceyoga.R
import com.bytezaptech.jawlineexercise_faceyoga.data.local.entities.LanguageEntity
import com.bytezaptech.jawlineexercise_faceyoga.databinding.LanguageListItemBinding
import com.bytezaptech.jawlineexercise_faceyoga.ui.settings.LanguageChangeDialogFragment

class LanguageAdapter(
    private val hostFrag: LanguageChangeDialogFragment,
    diffUtil: DiffUtil.ItemCallback<LanguageEntity>
) : ListAdapter<LanguageEntity, LanguageAdapter.LanguageViewHolder>(diffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LanguageViewHolder {
        val binding = DataBindingUtil.inflate<LanguageListItemBinding>(
            LayoutInflater.from(parent.context),
            R.layout.language_list_item,
            parent,
            false
        )
        return LanguageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LanguageViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class LanguageViewHolder(private val binding: LanguageListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(data: LanguageEntity) {
            binding.tvLanguage.text = data.name

            if (data.isSelected)
                binding.ivIc.visibility = View.VISIBLE
            else
                binding.ivIc.visibility = View.GONE

            binding.root.setOnClickListener {
                if (!data.isSelected)
                    hostFrag.viewModel.setLanguage(data)
                else
                    hostFrag.dialog?.dismiss()
            }
        }
    }
}