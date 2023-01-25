package com.plub.presentation.ui.home.adapter.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.plub.domain.model.vo.home.GatheringItemVo
import com.plub.presentation.databinding.LayoutRecyclerRecommendGatheringGridItemBinding

class MainRecommendGridViewHolder(
    private val binding: LayoutRecyclerRecommendGatheringGridItemBinding
): RecyclerView.ViewHolder(binding.root){
    fun bind(item: GatheringItemVo) {
        binding.apply {
            //iconCategory = item.img_res
            textViewGridItemTitle.text = item.title
            textViewGridItemIntro.text = item.intro
        }
    }
}