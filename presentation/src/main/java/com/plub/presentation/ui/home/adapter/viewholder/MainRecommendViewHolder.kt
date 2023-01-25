package com.plub.presentation.ui.home.adapter.viewholder

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.plub.domain.model.vo.home.recommendationgatheringvo.RecommendationGatheringDataResponseVo
import com.plub.presentation.databinding.IncludeItemLayoutMainRecommendGatheringBinding
import com.plub.presentation.ui.home.adapter.MainRecommendAdapter
import com.plub.presentation.ui.home.adapter.MainRecommendGatheringAdapter

class MainRecommendViewHolder(
    private val binding: IncludeItemLayoutMainRecommendGatheringBinding,
    private val listener: MainRecommendGatheringAdapter.Delegate
): RecyclerView.ViewHolder(binding.root){

    private val listAdapter = MainRecommendAdapter(listener)

    init {
        binding.recyclerRecommendMeetList.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = listAdapter
        }

    }
    fun bind(item: RecommendationGatheringDataResponseVo) {
        binding.apply {
            listAdapter.submitList(item.content)
        }
    }
}