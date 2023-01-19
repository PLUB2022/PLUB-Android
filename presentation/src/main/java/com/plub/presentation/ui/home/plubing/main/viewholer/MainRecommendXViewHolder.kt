package com.plub.presentation.ui.home.plubing.main.viewholer

import androidx.recyclerview.widget.RecyclerView
import com.plub.presentation.databinding.IncludeItemLayoutMainRecommendGatheringNoChocieBinding
import com.plub.presentation.ui.home.adapter.MainRecommendGatheringXAdapter

class MainRecommendXViewHolder(
    private val binding: IncludeItemLayoutMainRecommendGatheringNoChocieBinding,
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