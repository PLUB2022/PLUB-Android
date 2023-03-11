package com.plub.presentation.ui.main.archive.dialog

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.plub.presentation.databinding.IncludeItemDialogArchiveImageBinding

class ArchiveViewPagerAdapter: ListAdapter<String, RecyclerView.ViewHolder>(
    ArchiveViewPagerDiffCallback())
{

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ArchiveViewPagerViewHolder -> holder.bind(currentList[position])
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = IncludeItemDialogArchiveImageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ArchiveViewPagerViewHolder(binding)
    }
}

class ArchiveViewPagerDiffCallback : DiffUtil.ItemCallback<String>() {
    override fun areItemsTheSame(oldItem: String, newItem: String): Boolean = oldItem == newItem
    override fun areContentsTheSame(oldItem: String, newItem: String): Boolean = oldItem == newItem
}