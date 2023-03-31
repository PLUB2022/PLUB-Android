package com.plub.presentation.ui.main.home.recruitment.bottomsheet

import androidx.recyclerview.widget.RecyclerView
import com.plub.domain.model.vo.home.recruitDetailVo.RecruitDetailJoinedAccountsVo
import com.plub.presentation.databinding.IncludeItemCircleProfileDetailBinding
import com.plub.presentation.util.GlideUtil
import com.plub.presentation.util.onThrottleClick

class BottomSheetProfileViewHolder(
    private val binding: IncludeItemCircleProfileDetailBinding,
    private val listener: BottomSheetProfileAdapter.ProfileDelegate
) : RecyclerView.ViewHolder(binding.root) {


    private var vo: RecruitDetailJoinedAccountsVo? = null

    init {
        binding.imageViewProfile.onThrottleClick {
            vo?.let { listener.onProfileClick(it.accountId, it.nickname) }
        }
    }

    fun bind(item: RecruitDetailJoinedAccountsVo) {
        vo = item
        binding.apply {
            item.profileImage?.let { GlideUtil.loadImage(root.context, it, imageViewProfile) }
            imageViewProfile.clipToOutline = true
            textViewProfileName.text = item.nickname
        }
    }
}