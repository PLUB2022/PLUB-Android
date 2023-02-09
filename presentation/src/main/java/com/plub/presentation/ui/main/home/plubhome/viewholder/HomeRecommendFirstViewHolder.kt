package com.plub.presentation.ui.main.home.plubhome.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.plub.presentation.databinding.IncludeItemHomeRecommendFirstBinding
import com.plub.presentation.ui.main.home.plubhome.adapter.HomeRecommendGatheringAdapter

class HomeRecommendFirstViewHolder(
    private val binding: IncludeItemHomeRecommendFirstBinding,
    private val listener: HomeRecommendGatheringAdapter.HomeRecommendGatheringDelegate
): RecyclerView.ViewHolder(binding.root){

    init {
        binding.imageBtnSetting.setOnClickListener {
            listener.onClickSetting()
        }
    }

    fun bind() {
        binding.apply {

        }
    }
}