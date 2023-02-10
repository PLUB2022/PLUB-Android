package com.plub.presentation.ui.main.home.recruitment.bottomsheet

import androidx.recyclerview.widget.RecyclerView
import com.plub.domain.model.vo.home.recruitdetailvo.RecruitDetailJoinedAccountsListVo
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

    fun bind(item: RecruitDetailJoinedAccountsListVo) {
        binding.apply {
            GlideUtil.loadImage(root.context, item.profileImage, imageViewProfile)
            textViewProfileName.text = "나는조경석"
            accountId = item.accountId
        }
    }
}