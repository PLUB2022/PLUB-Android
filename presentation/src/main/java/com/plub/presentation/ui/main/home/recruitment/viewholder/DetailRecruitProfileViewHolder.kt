package com.plub.presentation.ui.main.home.recruitment.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.plub.domain.model.vo.home.recruitdetailvo.RecruitDetailJoinedAccountsListVo
import com.plub.presentation.R
import com.plub.presentation.databinding.IncludeItemCircleProfileBinding
import com.plub.presentation.ui.main.home.recruitment.adapter.DetailRecruitProfileAdapter
import com.plub.presentation.util.GlideUtil

class DetailRecruitProfileViewHolder(
    private val binding: IncludeItemCircleProfileBinding,
    private val listener: DetailRecruitProfileAdapter.DetailProfileDelegate
) : RecyclerView.ViewHolder(binding.root) {

    companion object {
        private const val MAX_PROFILE = 8
    }

    private var maxProfile : Int = 0
    private var nowNum : Int = 0
    private var accountId : Int = 0

    init {
        binding.imageViewProfile.setOnClickListener {
            if(nowNum == maxProfile){
                listener.onSeeMoreProfileClick()
            }
            else{
                listener.onProfileClick(accountId)
            }
        }
    }

    fun bind(item: RecruitDetailJoinedAccountsListVo, position: Int, allPeopleCount: Int, maxProfile : Int) {
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