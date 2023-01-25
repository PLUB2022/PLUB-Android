package com.plub.presentation.ui.home.adapter.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.plub.domain.model.vo.home.GatheringItemVo
import com.plub.presentation.databinding.LayoutRecyclerRecommendGatheringListItemBinding

class MainRecommendViewHolder(
    private val binding: LayoutRecyclerRecommendGatheringListItemBinding
): RecyclerView.ViewHolder(binding.root){
    fun bind(item: GatheringItemVo) {
        binding.apply {
            //iconCategory = item.img_res
            tvMeetTitle.text = item.title
            tvMeetOnelineIntroduce.text = item.intro
        }
    }
}