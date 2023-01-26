package com.plub.presentation.ui.home.plubing.main.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.plub.presentation.databinding.IncludeItemLayoutMainRecommendGatheringNoChoiceBinding
import com.plub.presentation.ui.home.plubing.main.adapter.MainRecommendGatheringXAdapter

class MainRecommendXViewHolder(
    private val binding: IncludeItemLayoutMainRecommendGatheringNoChoiceBinding,
    private val listener: MainRecommendGatheringXAdapter.MainRecommendGatheringXDelegate
): RecyclerView.ViewHolder(binding.root){

    init {
        binding.constraintLayoutRegisterInterest.setOnClickListener {
            listener.onClick()
        }
    }

    fun bind(item: Int) {
        binding.apply {

        }
    }
}