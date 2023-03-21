package com.plub.presentation.ui.main.plubing.schedule.adapter.scheduleCard

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.plub.domain.model.enums.ScheduleCardType
import com.plub.domain.model.vo.schedule.ScheduleVo
import com.plub.presentation.databinding.LayoutRecyclerPlubingScheduleContentBinding
import com.plub.presentation.databinding.LayoutRecyclerPlubingScheduleLoadingBinding
import com.plub.presentation.databinding.LayoutRecyclerPlubingScheduleYearBinding

class PlubingScheduleAdapter(
    private val onClick: ((scheduleVo: ScheduleVo) -> Unit)? = null,
    private val onLongClick: ((scheduleVo: ScheduleVo) -> Unit)? = null
) : ListAdapter<ScheduleVo, RecyclerView.ViewHolder>(ScheduleDiffCallback()) {

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is PlubingScheduleContentViewHolder -> holder.bind(currentList, currentList[position], position)
            is PlubingScheduleLoadingViewHolder -> { }
            is PlubingScheduleYearViewHolder -> holder.bind(currentList[position].startedYear)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (ScheduleCardType.indexOf(viewType)) {
            ScheduleCardType.YEAR -> {
                val binding = LayoutRecyclerPlubingScheduleYearBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                PlubingScheduleYearViewHolder(binding)
            }
            ScheduleCardType.CONTENT -> {
                val binding = LayoutRecyclerPlubingScheduleContentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                PlubingScheduleContentViewHolder(binding, onClick, onLongClick)
            }
            ScheduleCardType.LOADING -> {
                val binding = LayoutRecyclerPlubingScheduleLoadingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                PlubingScheduleLoadingViewHolder(binding)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return currentList[position].viewType.idx
    }
}

class ScheduleDiffCallback : DiffUtil.ItemCallback<ScheduleVo>() {
    override fun areItemsTheSame(oldItem: ScheduleVo, newItem: ScheduleVo): Boolean =
        oldItem.calendarId == newItem.calendarId && oldItem.viewType == newItem.viewType

    override fun areContentsTheSame(oldItem: ScheduleVo, newItem: ScheduleVo): Boolean =
        oldItem == newItem
}