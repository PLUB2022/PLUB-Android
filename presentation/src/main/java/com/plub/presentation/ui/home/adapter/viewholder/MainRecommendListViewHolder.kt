package com.plub.presentation.ui.home.adapter.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.plub.domain.model.vo.home.recommendationgatheringvo.RecommendationGatheringResponseContentListVo
import com.plub.presentation.databinding.IncludeItemRecommendGatheringListItemBinding
import com.plub.presentation.ui.home.adapter.MainRecommendGatheringAdapter

class MainRecommendListViewHolder(
    private val binding: IncludeItemRecommendGatheringListItemBinding,
    private val listener : MainRecommendGatheringAdapter.MainRecommendGatheringDelegate
): RecyclerView.ViewHolder(binding.root){

    fun bind(item: RecommendationGatheringResponseContentListVo) {
        binding.apply {
            constraintLayoutItemLayout.setOnClickListener{
                listener.onClick(item.plubbingId)
            }


        }
    }
}