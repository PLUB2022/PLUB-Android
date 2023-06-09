package com.plub.presentation.ui.main.archive.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.plub.domain.model.enums.ArchiveAccessType
import com.plub.domain.model.enums.ArchiveViewType
import com.plub.domain.model.enums.HomeViewType
import com.plub.domain.model.vo.archive.ArchiveContentResponseVo
import com.plub.presentation.databinding.*
import com.plub.presentation.ui.main.home.plubhome.viewholder.*
import com.plub.presentation.ui.main.home.progress.LoadingViewHolder
import com.plub.presentation.ui.main.plubing.schedule.adapter.scheduleCard.PlubingScheduleLoadingViewHolder

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
            is LoadingViewHolder -> holder.bind()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(ArchiveViewType.valueOf(viewType)){
            ArchiveViewType.ARCHIVE -> {
                val binding = IncludeItemArchiveBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                ArchiveViewHolder(binding, listener)
            }
            ArchiveViewType.LOADING -> {
                val binding = IncludeItemProgressBarBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                LoadingViewHolder(binding)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return currentList[position].viewType.type
    }
}

class ArchiveDiffCallback : DiffUtil.ItemCallback<ArchiveContentResponseVo>() {
    override fun areItemsTheSame(oldItem: ArchiveContentResponseVo, newItem: ArchiveContentResponseVo): Boolean = oldItem.archiveId == newItem.archiveId
    override fun areContentsTheSame(oldItem: ArchiveContentResponseVo, newItem: ArchiveContentResponseVo): Boolean = oldItem == newItem
}