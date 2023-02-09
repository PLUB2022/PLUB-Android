package com.plub.presentation.ui.main.home.plubhome.viewholder

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.plub.domain.model.vo.home.recommendationgatheringvo.RecommendationGatheringResponseVo
import com.plub.presentation.databinding.IncludeItemLayoutHomeRecommendGatheringBinding
import com.plub.presentation.ui.main.home.plubhome.adapter.HomeRecommendAdapter
import com.plub.presentation.ui.main.home.plubhome.adapter.HomeRecommendGatheringAdapter

class HomeRecommendViewHolder(
    private val binding: IncludeItemLayoutHomeRecommendGatheringBinding,
    private val listener: HomeRecommendGatheringAdapter.HomeRecommendGatheringDelegate
): RecyclerView.ViewHolder(binding.root){

    private val listAdapter = HomeRecommendAdapter(listener)

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