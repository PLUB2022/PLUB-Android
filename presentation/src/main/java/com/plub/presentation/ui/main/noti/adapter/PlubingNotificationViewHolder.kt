package com.plub.presentation.ui.main.noti.adapter

import android.content.res.ColorStateList
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.plub.domain.model.enums.NotificationType
import com.plub.domain.model.enums.NotificationType.*
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
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOf

class PlubingNotificationViewHolder(
    private val binding: LayoutRecyclerNotificationBinding,
    val onClick: (NotificationResponseVo) -> Unit = { }
) : RecyclerView.ViewHolder(binding.root) {

    private var item: NotificationResponseVo? = null
    private val contextReadColor: Int = R.color.color_8c8c8c
    private val contextUnReadColor: Int = R.color.color_363636

    private val backgroundReadColor: Int = R.color.color_f2f3f4
    private val backgroundUnReadColor: Int = R.color.color_faf9fe

    init {
        binding.root.onThrottleClick {
            item?.let { onClick(it) }
        }
    }

    fun bind(item: NotificationResponseVo) {
        this.item = item
        binding.apply {
            listOf(textViewTitle, textViewBody).forEach { textView ->
                textView.setTextColor(ContextCompat.getColor(root.context, if(item.isRead) contextReadColor else contextUnReadColor))
            }

            imageViewIcon.apply {
                setImageResource(setIcon(item.notificationType))
                imageTintList = ColorStateList.valueOf(ContextCompat.getColor(root.context, if(item.isRead) contextReadColor else contextUnReadColor))
            }
            root.setBackgroundColor(ContextCompat.getColor(root.context, if(item.isRead) backgroundReadColor else backgroundUnReadColor))

            textViewTitle.text = item.title
            textViewBody.text = item.body
            textViewDate.text = item.createdAt
        }
    }

    private fun setIcon(type: String): Int {
        return when(valueOf(type)) {
            REPORTED_ONCE, BAN_ONE_MONTH, BAN_PERMANENTLY, KICK_MEMBER, UNBAN -> R.drawable.ic_noti_notice
            APPLY_RECRUIT, LEAVE_PLUBBING, APPROVE_RECRUIT, CREATE_NOTICE, CREATE_UPDATE_CALENDAR -> R.drawable.ic_noti_plub
            CREATE_FEED_COMMENT, CREATE_FEED_COMMENT_COMMENT, PINNED_FEED -> R.drawable.ic_noti_comment
        }
    }
}