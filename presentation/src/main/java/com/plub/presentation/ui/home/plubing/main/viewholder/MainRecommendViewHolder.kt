package com.plub.presentation.ui.home.plubing.main.viewholder

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.plub.domain.model.vo.home.recommendationgatheringvo.RecommendationGatheringResponseVo
import com.plub.domain.model.vo.plub.PlubCardListVo
import com.plub.presentation.databinding.IncludeItemLayoutMainRecommendGatheringBinding
import com.plub.presentation.ui.home.plubing.main.adapter.MainRecommendAdapter
import com.plub.presentation.ui.home.plubing.main.adapter.MainRecommendGatheringAdapter

class MainRecommendViewHolder(
    private val binding: IncludeItemLayoutMainRecommendGatheringBinding,
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