package com.plub.presentation.ui.main.noti.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.plub.domain.model.enums.ScheduleCardType
import com.plub.domain.model.vo.notification.NotificationResponseVo
import com.plub.domain.model.vo.schedule.ScheduleVo
import com.plub.presentation.databinding.LayoutRecyclerNotificationBinding
import com.plub.presentation.databinding.LayoutRecyclerPlubingScheduleContentBinding
import com.plub.presentation.databinding.LayoutRecyclerPlubingScheduleLoadingBinding
import com.plub.presentation.databinding.LayoutRecyclerPlubingScheduleYearBinding

class PlubingNotificationAdapter(
) : ListAdapter<NotificationResponseVo, RecyclerView.ViewHolder>(NotificationResponseDiffCallback()) {

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is PlubingNotificationViewHolder -> holder.bind(
                currentList[position]
            )
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = LayoutRecyclerNotificationBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return PlubingNotificationViewHolder(binding)
    }
}

class NotificationResponseDiffCallback : DiffUtil.ItemCallback<NotificationResponseVo>() {
    override fun areItemsTheSame(
        oldItem: NotificationResponseVo,
        newItem: NotificationResponseVo
    ): Boolean =
        oldItem.notificationId == newItem.notificationId

    override fun areContentsTheSame(
        oldItem: NotificationResponseVo,
        newItem: NotificationResponseVo
    ): Boolean =
        oldItem == newItem
}