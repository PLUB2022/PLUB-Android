package com.plub.presentation.ui.main.plubing.schedule.bottomSheet

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.plub.domain.model.vo.schedule.CalendarAttendVo
import com.plub.presentation.databinding.IncludeItemCircleProfileBinding
import com.plub.presentation.databinding.LayoutRecyclerScheduleDetailExpandProfileBinding
import com.plub.presentation.databinding.LayoutRecyclerScheduleDetailFoldProfileBinding
import com.plub.presentation.ui.main.home.recruitment.viewholder.DetailRecruitProfileViewHolder

class ExpandProfileAdapter() : ListAdapter<CalendarAttendVo, RecyclerView.ViewHolder>(
    ProfileExpandDiffCallBack()
) {

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ExpandProfileViewHolder -> {
                holder.bind(currentList[position])
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = LayoutRecyclerScheduleDetailExpandProfileBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ExpandProfileViewHolder(binding)
    }
}

class ProfileExpandDiffCallBack :
    DiffUtil.ItemCallback<CalendarAttendVo>() {
    override fun areItemsTheSame(
        oldItem: CalendarAttendVo,
        newItem: CalendarAttendVo
    ): Boolean = oldItem.calendarAttendId == newItem.calendarAttendId

    override fun areContentsTheSame(
        oldItem: CalendarAttendVo,
        newItem: CalendarAttendVo
    ): Boolean = oldItem == newItem
}