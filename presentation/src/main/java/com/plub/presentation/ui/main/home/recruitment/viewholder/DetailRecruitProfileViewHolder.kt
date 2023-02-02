package com.plub.presentation.ui.home.plubing.recruitment.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.plub.domain.model.vo.home.recruitdetailvo.RecruitDetailJoinedAccountsListVo
import com.plub.presentation.R
import com.plub.presentation.databinding.IncludeItemCircleProfileBinding
import com.plub.presentation.ui.main.home.recruitment.adapter.DetailRecruitProfileAdapter

class DetailRecruitProfileViewHolder(
    private val binding: IncludeItemCircleProfileBinding,
    private val listener: DetailRecruitProfileAdapter.DetailProfileDegelate
) : RecyclerView.ViewHolder(binding.root) {

    companion object {
        private const val MAX_PROFILE = 8
    }

    private var accountId : Int = 0

    init {
        binding.imageViewProfile.setOnClickListener {
            listener.onProfileClick(accountId)
        }
    }

    fun bind(item: RecruitDetailJoinedAccountsListVo, nowNum: Int, peopleCount : Int) {
        binding.apply {
            //imageViewProfile = item
            if (nowNum == MAX_PROFILE) {
                textViewMoreProfileNumber.text = root.context.getString(R.string.detail_recruitment_profile_county, peopleCount)
            }
            accountId = item.accountId
        }
    }
}