package com.plub.presentation.ui.main.home.profile.viewHolder.detail

import androidx.recyclerview.widget.RecyclerView
import com.plub.domain.model.vo.myPage.MyPageApplicationsVo
import com.plub.presentation.R
import com.plub.presentation.databinding.IncludeItemApplicationBinding
import com.plub.presentation.util.GlideUtil

class MyPageDetailApplicationViewHolder(
    private val binding: IncludeItemApplicationBinding,
) : RecyclerView.ViewHolder(binding.root) {

    init {
    }

    fun bind(item: MyPageApplicationsVo) {
        binding.apply {
            GlideUtil.loadImage(root.context, item.profileImage, imageViewProfile)
            imageViewProfile.clipToOutline = true
            textViewName.text = item.name
            textViewDate.text = root.context.getString(R.string.my_page_complete_answer, item.date)
        }
    }
}