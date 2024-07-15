package com.bytezaptech.jawlineexercise_faceyoga.adapters

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bytezaptech.jawlineexercise_faceyoga.R
import com.bytezaptech.jawlineexercise_faceyoga.data.local.entities.GrowthEntity
import com.bytezaptech.jawlineexercise_faceyoga.databinding.GrowthListItemBinding
import com.bytezaptech.jawlineexercise_faceyoga.utils.showMessageDialog
import java.io.File
import java.io.FileDescriptor
import java.io.FileNotFoundException

class GrowthListAdapter(val context: Context, diffUtil: DiffUtil.ItemCallback<GrowthEntity>): ListAdapter<GrowthEntity, GrowthListAdapter.ViewHolder>(diffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = DataBindingUtil.inflate<GrowthListItemBinding>(LayoutInflater.from(parent.context), R.layout.growth_list_item, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(val binding: GrowthListItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(data: GrowthEntity) {
            binding.totalExTv.text = "${data.day} / ${data.totalDays}"

            data.growthImg?.let {
                try {
                    val parcelFileDescriptor = context.contentResolver.openFileDescriptor(
                        Uri.parse(data.growthImg), "r")
                    val fileDescriptor = parcelFileDescriptor?.fileDescriptor
                    val image: Bitmap = BitmapFactory.decodeFileDescriptor(fileDescriptor)

                    binding.ivGrowth.setImageBitmap(image)
                } catch (e: FileNotFoundException) {
                    binding.totalExTv.text = "File not found of ${data.day}"
                } catch (ignore: Exception) {}
            }
        }
    }
}