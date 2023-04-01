package com.plub.presentation.ui.main.plubing.schedule.bottomSheet

import androidx.recyclerview.widget.RecyclerView
import com.plub.presentation.R
import com.plub.presentation.databinding.LayoutRecyclerScheduleDetailFoldProfileBinding
import com.plub.presentation.util.GlideUtil

class FoldProfileViewHolder(
    private val binding: LayoutRecyclerScheduleDetailFoldProfileBinding
) : RecyclerView.ViewHolder(binding.root) {

    init {

    }

    fun bind(item: String, position: Int, allPeopleCount: Int, maxProfile : Int) {
        val nowNum = position + 1
        val morePeopleCount = allPeopleCount - position
        binding.apply {
            if (nowNum == maxProfile) {
                textViewMoreProfileNumber.text = root.context.getString(R.string.detail_recruitment_profile_county, morePeopleCount)
            }
            else{
                GlideUtil.loadImage(root.context, item, imageViewProfile, R.drawable.iv_default_profile)
                imageViewProfile.clipToOutline = true
            }
        }
    }
}