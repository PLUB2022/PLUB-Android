package com.plub.presentation.ui.main.archive.bottomsheet.dots.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.plub.domain.model.enums.ArchiveMenuType
import com.plub.presentation.databinding.*

class ArchiveDotsMenuAdapter(private val listener: ArchiveDotsMenuDelegate) : ListAdapter<ArchiveMenuType, RecyclerView.ViewHolder>(
    ArchiveDotsMenuDiffCallback()
) {

    interface ArchiveDotsMenuDelegate {
        fun onClick(archiveMenuType: ArchiveMenuType)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ArchiveEditMenuViewHolder -> holder.bind(currentList[position])
            is ArchiveReportMenuViewHolder -> holder.bind(currentList[position])
            is ArchiveDeleteMenuViewHolder -> holder.bind(currentList[position])
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(ArchiveMenuType.valueOf(viewType)){
            ArchiveMenuType.EDIT -> {
                val binding = IncludeItemArchiveEditBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                ArchiveEditMenuViewHolder(binding, listener)
            }
            ArchiveMenuType.REPORT -> {
                val binding = IncludeItemArchiveReportBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                ArchiveReportMenuViewHolder(binding, listener)
            }
            ArchiveMenuType.DELETE -> {
                val binding = IncludeItemArchiveDeleteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                ArchiveDeleteMenuViewHolder(binding, listener)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return currentList[position].type
    }
}

class ArchiveDotsMenuDiffCallback : DiffUtil.ItemCallback<ArchiveMenuType>() {
    override fun areItemsTheSame(oldItem: ArchiveMenuType, newItem: ArchiveMenuType): Boolean = oldItem.type == newItem.type
    override fun areContentsTheSame(oldItem: ArchiveMenuType, newItem: ArchiveMenuType): Boolean = oldItem == newItem
}