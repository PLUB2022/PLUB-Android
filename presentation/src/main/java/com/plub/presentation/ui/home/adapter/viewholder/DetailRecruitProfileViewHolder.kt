
package com.plub.presentation.ui.home.adapter.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.plub.domain.model.vo.home.recruitdetailvo.RecruitDetailJoinedAccountsListVo
import com.plub.presentation.databinding.IncludeItemCircleProfileBinding
import com.plub.presentation.ui.home.adapter.DetailRecruitProfileAdapter

class DetailRecruitProfileViewHolder(
    private val binding: IncludeItemCircleProfileBinding,
    private val listener: DetailRecruitProfileAdapter.DetailProfileDegelate
) : RecyclerView.ViewHolder(binding.root) {


    fun bind(item: RecruitDetailJoinedAccountsListVo, size: Int, nowNum: Int) {
        binding.apply {
            //imageViewProfile = item
            if (nowNum == 8) {
                textViewMoreProfileNumber.text = "+${nowNum}"
            }
            imageViewProfile.setOnClickListener {
                listener.onProfileClick(item.accountId)
            }
        }
    }
}