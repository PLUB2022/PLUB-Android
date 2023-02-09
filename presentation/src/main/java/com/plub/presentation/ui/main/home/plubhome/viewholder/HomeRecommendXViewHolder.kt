package com.plub.presentation.ui.main.home.plubhome.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.plub.presentation.databinding.IncludeItemLayoutHomeRecommendGatheringNoChoiceBinding
import com.plub.presentation.ui.main.home.plubhome.adapter.HomeRecommendGatheringAdapter

class HomeRecommendXViewHolder(
    private val binding: IncludeItemLayoutHomeRecommendGatheringNoChoiceBinding,
    private val listener: HomeRecommendGatheringAdapter.HomeRecommendGatheringDelegate
): RecyclerView.ViewHolder(binding.root){

    init {
        binding.constraintLayoutRegisterInterest.setOnClickListener {
            listener.onClickRegister()
        }
    }

    fun bind() {
        binding.apply {

        }
    }
}