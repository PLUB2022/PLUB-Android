package com.plub.presentation.ui.home.adapter.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.plub.domain.model.vo.home.recommendationgatheringvo.RecommendationGatheringDataResponseVo
import com.plub.presentation.databinding.IncludeItemLayoutMainRecommendGatheringNoChocieBinding
import com.plub.presentation.ui.home.adapter.MainRecommendGatheringXAdapter

class MainRecommendXViewHolder(
    private val binding: IncludeItemLayoutMainRecommendGatheringNoChocieBinding,
    private val listener: MainRecommendGatheringXAdapter.Delegate
): RecyclerView.ViewHolder(binding.root){

    fun bind(item: Int) {
        binding.apply {
            //constraintLayoutRegisterInterest. 어쩌구 저쩌구
        }
    }
}