package com.plub.presentation.ui.main.home.recruitment.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.plub.domain.model.vo.home.recruitDetailVo.RecruitDetailJoinedAccountsVo
import com.plub.presentation.R
import com.plub.presentation.databinding.IncludeItemCircleProfileBinding
import com.plub.presentation.ui.main.home.recruitment.adapter.DetailRecruitProfileAdapter
import com.plub.presentation.util.GlideUtil
import com.plub.presentation.util.onThrottleClick

class DetailRecruitProfileViewHolder(
    private val binding: IncludeItemCircleProfileBinding,
    private val listener: DetailRecruitProfileAdapter.DetailProfileDelegate
) : RecyclerView.ViewHolder(binding.root) {

    private var maxProfile : Int = 0
    private var nowNum : Int = 0
    private var accountId : Int? = null

    init {
        binding.imageViewProfile.onThrottleClick {
            if(nowNum == maxProfile){
                listener.onSeeMoreProfileClick()
            }
            else{
                accountId?.let { listener.onProfileClick(it) }
            }
        }
    }

    fun bind(item: RecruitDetailJoinedAccountsVo, position: Int, allPeopleCount: Int, maxProfile : Int) {
        this.maxProfile = maxProfile
        nowNum = position + 1
        val morePeopleCount = allPeopleCount - position
        binding.apply {
            if (nowNum == maxProfile) {
                textViewMoreProfileNumber.text = root.context.getString(R.string.detail_recruitment_profile_county, morePeopleCount)
            }
            else{
                GlideUtil.loadImage(root.context, item.profileImage, imageViewProfile)
                imageViewProfile.clipToOutline = true
            }
            accountId = item.accountId
        }
    }
}