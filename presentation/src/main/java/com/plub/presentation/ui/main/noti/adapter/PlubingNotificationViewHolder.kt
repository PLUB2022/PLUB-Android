package com.plub.presentation.ui.main.noti.adapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.plub.domain.model.enums.ScheduleCardType
import com.plub.domain.model.vo.notification.NotificationResponseVo
import com.plub.domain.model.vo.schedule.ScheduleVo
import com.plub.presentation.R
import com.plub.presentation.databinding.LayoutRecyclerNotificationBinding
import com.plub.presentation.databinding.LayoutRecyclerPlubingScheduleContentBinding
import com.plub.presentation.ui.common.decoration.OverlapDecoration
import com.plub.presentation.ui.main.plubing.schedule.adapter.scheduleCard.profileFold.PlubingScheduleProfileAdapter
import com.plub.presentation.util.PlubLogger
import com.plub.presentation.util.TimeFormatter
import com.plub.presentation.util.onThrottleClick

class PlubingNotificationViewHolder(
    private val binding: LayoutRecyclerNotificationBinding,
) : RecyclerView.ViewHolder(binding.root) {

    init {

    }

    fun bind(item: NotificationResponseVo) {
        binding.apply {
            textViewTitle.text = item.title
            textViewBody.text = item.body
            textViewDate.text = item.createdAt
        }
    }
}