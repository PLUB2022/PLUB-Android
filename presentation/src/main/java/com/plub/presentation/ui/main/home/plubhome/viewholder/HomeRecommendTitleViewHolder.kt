package com.plub.presentation.ui.main.home.plubhome.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.plub.presentation.databinding.IncludeItemHomeRecommendFirstBinding
import com.plub.presentation.ui.main.home.plubhome.adapter.HomeAdapter
import com.plub.presentation.util.onThrottleClick

class HomeRecommendTitleViewHolder(
    private val binding: IncludeItemHomeRecommendFirstBinding,
    private val listener: HomeAdapter.HomeDelegate
): RecyclerView.ViewHolder(binding.root){

    init {
        binding.imageViewSetting.onThrottleClick {
            listener.onClickSetting()
        }
    }

    fun bind() {
        binding.apply {

        }
    }
}