package com.plub.presentation.ui.home.adapter.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.plub.domain.model.vo.home.recommendationgatheringvo.RecommendationGatheringResponseContentListVo
import com.plub.presentation.databinding.IncludeItemRecommendGatheringGridBinding
import com.plub.presentation.ui.home.adapter.MainRecommendGridAdapter

class MainRecommendGridViewHolder(
    private val binding: IncludeItemRecommendGatheringGridBinding,
    private val listener : MainRecommendGridAdapter.MainRecommendGridDelegate
): RecyclerView.ViewHolder(binding.root){

    fun bind(item: RecommendationGatheringResponseContentListVo) {
        binding.apply {
            imageViewRecommendGrid.setOnClickListener {
                listener.onClick(item.plubbingId)
            }

        }
    }
}