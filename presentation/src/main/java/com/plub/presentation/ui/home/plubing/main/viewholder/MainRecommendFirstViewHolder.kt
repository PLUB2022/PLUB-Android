package com.plub.presentation.ui.home.plubing.main.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.plub.presentation.databinding.IncludeItemLayoutMainRecommendGatheringNoChoiceBinding
import com.plub.presentation.databinding.IncludeItemMainRecommendFirstBinding
import com.plub.presentation.ui.home.plubing.main.adapter.MainRecommendGatheringAdapter

class MainRecommendFirstViewHolder(
    private val binding: IncludeItemMainRecommendFirstBinding,
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