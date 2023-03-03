package com.plub.presentation.ui.main.profile.adapter

import androidx.recyclerview.widget.RecyclerView
import com.plub.domain.model.vo.myPage.MyPageGatheringVo
import com.plub.presentation.R
import com.plub.presentation.databinding.IncludeItemMyGatheringBinding

class MyPageParentGatheringViewHolder(
    private val binding: IncludeItemMyGatheringBinding,
) : RecyclerView.ViewHolder(binding.root) {

    init {

    }

    fun bind(item: MyPageGatheringVo) {
        binding.apply {
            when(item.gatheringType){
                0 -> {textViewGatheringType.text = root.context.getString(R.string.my_page_recruiting_gathering)}
                1 -> {textViewGatheringType.text = root.context.getString(R.string.my_page_waiting_gathering)}
                2 -> {textViewGatheringType.text = root.context.getString(R.string.my_page_active_gathering)}
                3 -> {textViewGatheringType.text = root.context.getString(R.string.my_page_end_gathering)}
            }
        }
    }
}