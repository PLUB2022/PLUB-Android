package com.plub.presentation.ui.home.adapter.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.plub.domain.model.vo.home.recommendationgatheringvo.RecommendationGatheringResponseContentListVo
import com.plub.presentation.databinding.IncludeItemRecommendGatheringListItemBinding

class MainRecommendListViewHolder(
    private val binding: IncludeItemRecommendGatheringListItemBinding
): RecyclerView.ViewHolder(binding.root){
    fun bind(item: RecommendationGatheringResponseContentListVo) {
        binding.apply {
            //iconCategory = item.img_res
//            tvMeetTitle.text = item.title
//            tvMeetOnelineIntroduce.text = item.intro


        }
    }
}