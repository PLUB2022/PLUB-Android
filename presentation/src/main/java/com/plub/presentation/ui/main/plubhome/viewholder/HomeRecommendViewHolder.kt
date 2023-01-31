package com.plub.presentation.ui.home.plubing.plubhome.viewholder

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.plub.domain.model.vo.home.recommendationgatheringvo.RecommendationGatheringResponseVo
import com.plub.presentation.databinding.IncludeItemLayoutHomeRecommendGatheringBinding
import com.plub.presentation.ui.home.plubing.plubhome.adapter.MainRecommendAdapter
import com.plub.presentation.ui.home.plubing.plubhome.adapter.MainRecommendGatheringAdapter

class HomeRecommendViewHolder(
    private val binding: IncludeItemLayoutHomeRecommendGatheringBinding,
    private val listener: MainRecommendGatheringAdapter.MainRecommendGatheringDelegate
): RecyclerView.ViewHolder(binding.root){

    private val listAdapter = MainRecommendAdapter(listener)

    init {
        binding.recyclerViewRecommendMeetList.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = listAdapter
        }

    }
    fun bind(item: RecommendationGatheringResponseVo) {
        binding.apply {
            listAdapter.submitList(item.content)
        }
    }
}