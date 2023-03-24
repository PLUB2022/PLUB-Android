package com.plub.presentation.ui.main.plubing.schedule.bottomSheet

import androidx.recyclerview.widget.RecyclerView
import com.plub.domain.model.vo.schedule.CalendarAttendVo
import com.plub.presentation.R
import com.plub.presentation.databinding.LayoutRecyclerScheduleDetailExpandProfileBinding
import com.plub.presentation.util.GlideUtil

class ExpandProfileViewHolder(
    private val binding: LayoutRecyclerScheduleDetailExpandProfileBinding
) : RecyclerView.ViewHolder(binding.root) {

    init {

    }

    fun bind(item: CalendarAttendVo) {
        binding.apply {
            GlideUtil.loadImage(root.context, item.profileImage, imageViewProfile, R.drawable.iv_default_profile)
            imageViewProfile.clipToOutline = true
            textViewNickname.text = item.nickname
        }
    }
}