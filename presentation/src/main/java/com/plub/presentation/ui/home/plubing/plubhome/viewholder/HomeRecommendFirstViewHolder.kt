package com.plub.presentation.ui.home.plubing.plubhome.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.plub.presentation.databinding.IncludeItemHomeRecommendFirstBinding
import com.plub.presentation.ui.home.plubing.plubhome.adapter.MainRecommendGatheringAdapter

class HomeRecommendFirstViewHolder(
    private val binding: IncludeItemHomeRecommendFirstBinding,
    private val listener: MainRecommendGatheringAdapter.MainRecommendGatheringDelegate
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