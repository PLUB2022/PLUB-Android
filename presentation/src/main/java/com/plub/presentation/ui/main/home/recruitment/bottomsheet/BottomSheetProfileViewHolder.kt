package com.plub.presentation.ui.main.home.recruitment.bottomsheet

import androidx.recyclerview.widget.RecyclerView
import com.plub.domain.model.vo.home.recruitdetailvo.RecruitDetailJoinedAccountsVo
import com.plub.presentation.databinding.IncludeItemCircleProfileDetailBinding
import com.plub.presentation.util.GlideUtil

class BottomSheetProfileViewHolder(
    private val binding: IncludeItemCircleProfileDetailBinding,
    private val listener: BottomSheetProfileAdapter.ProfileDelegate
) : RecyclerView.ViewHolder(binding.root) {


    private var accountId: Int = 0

    init {
        binding.imageViewProfile.setOnClickListener {
            listener.onProfileClick(accountId)
        }
    }

    fun bind(item: RecruitDetailJoinedAccountsVo) {
        binding.apply {
            GlideUtil.loadImage(root.context, item.profileImage, imageViewProfile)
            imageViewProfile.clipToOutline = true
            textViewProfileName.text = item.nickname
            accountId = item.accountId
        }
    }
}