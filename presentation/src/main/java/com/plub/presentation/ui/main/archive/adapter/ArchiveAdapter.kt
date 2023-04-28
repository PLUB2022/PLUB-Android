package com.plub.presentation.ui.main.archive.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.plub.domain.model.enums.ArchiveAccessType
import com.plub.domain.model.vo.archive.ArchiveContentResponseVo
import com.plub.presentation.databinding.IncludeItemArchiveBinding

class ArchiveAdapter(private val listener: ArchiveDelegate) : ListAdapter<ArchiveContentResponseVo, RecyclerView.ViewHolder>(
    ArchiveDiffCallback()
) {

    interface ArchiveDelegate {
        fun onCardClick(archiveId : Int)
        fun onDotsClick(type : ArchiveAccessType, archiveId : Int)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ArchiveViewHolder -> holder.bind(currentList[position])
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = IncludeItemArchiveBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ArchiveViewHolder(binding, listener)
    }
}

class ArchiveDiffCallback : DiffUtil.ItemCallback<ArchiveContentResponseVo>() {
    override fun areItemsTheSame(oldItem: ArchiveContentResponseVo, newItem: ArchiveContentResponseVo): Boolean = oldItem.archiveId == newItem.archiveId
    override fun areContentsTheSame(oldItem: ArchiveContentResponseVo, newItem: ArchiveContentResponseVo): Boolean = oldItem == newItem
}