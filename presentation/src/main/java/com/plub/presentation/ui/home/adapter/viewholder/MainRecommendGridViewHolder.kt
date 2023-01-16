package com.plub.presentation.ui.home.adapter.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.plub.domain.model.vo.home.recommendationgatheringvo.RecommendationGatheringDataResponseVo
import com.plub.presentation.databinding.IncludeItemRecommendGatheringGridBinding

class MainRecommendGridViewHolder(
    private val binding: IncludeItemRecommendGatheringGridBinding
): RecyclerView.ViewHolder(binding.root){

    fun bind(item: RecommendationGatheringDataResponseVo) {
        binding.apply {
            //iconCategory = item.img_res
            //textViewPlubbingGatheringName.text = item.title
        }
    }
}