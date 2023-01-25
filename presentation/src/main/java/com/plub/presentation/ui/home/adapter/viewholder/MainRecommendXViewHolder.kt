package com.plub.presentation.ui.home.adapter.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.plub.presentation.databinding.IncludeItemLayoutMainRecommendGatheringNoChoiceBinding
import com.plub.presentation.ui.home.adapter.MainRecommendGatheringXAdapter

class MainRecommendXViewHolder(
    private val binding: IncludeItemLayoutMainRecommendGatheringNoChoiceBinding,
    private val listener: MainRecommendGatheringXAdapter.MainRecommendGatheringXDelegate
): RecyclerView.ViewHolder(binding.root){

    fun bind(item: Int) {
        binding.apply {
            //constraintLayoutRegisterInterest. 어쩌구 저쩌구
        }
    }
}