package com.bytezaptech.jawlineexercise_faceyoga.adapters

import android.app.Activity
import android.text.TextUtils.substring
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bytezaptech.jawlineexercise_faceyoga.R
import com.bytezaptech.jawlineexercise_faceyoga.data.local.entities.ArticleEntity
import com.bytezaptech.jawlineexercise_faceyoga.databinding.ArticleListItemBinding
import com.bytezaptech.jawlineexercise_faceyoga.ui.history.HistoryFragmentDirections

class ArticleAdapter(val activity: Activity, diffUtil: DiffUtil.ItemCallback<ArticleEntity>): ListAdapter<ArticleEntity, ArticleAdapter.ArticleViewHolder>(diffUtil) {

    inner class ArticleViewHolder(val binding: ArticleListItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(data: ArticleEntity) {
            binding.titleTv.text = data.title
            binding.descTv.text = if(data.description?.length!! > 100) data.description.substring(0, 50) + "..." else data.description

            binding.gif.setImageResource(data.image!!)

            binding.readMoreBtn.setOnClickListener {
                val action = HistoryFragmentDirections.actionHistoryToArticleDetailsSheetFragment(data)
                activity.findNavController(R.id.nav_host).navigate(action)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val binding: ArticleListItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.article_list_item, parent, false)
        return ArticleViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}