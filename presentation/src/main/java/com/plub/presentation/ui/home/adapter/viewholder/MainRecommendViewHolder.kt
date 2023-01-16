package com.plub.presentation.ui.home.adapter.viewholder

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.plub.domain.model.vo.home.GatheringItemVo
import com.plub.domain.model.vo.home.recommendationgatheringvo.RecommendationGatheringDataResponseVo
import com.plub.domain.model.vo.home.recommendationgatheringvo.RecommendationGatheringResponseContentListVo
import com.plub.presentation.R
import com.plub.presentation.databinding.IncludeItemLayoutMainRecommendGatheringBinding
import com.plub.presentation.databinding.IncludeItemRecommendGatheringListItemBinding
import com.plub.presentation.ui.home.adapter.MainCategoryAdapter
import com.plub.presentation.ui.home.adapter.MainCategoryItemAdapter
import com.plub.presentation.ui.home.adapter.MainRecommendAdapter
import com.plub.presentation.ui.home.adapter.MainRecommendGatheringAdapter

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
    fun bind(item: RecommendationGatheringDataResponseVo) {
        binding.apply {
            listAdapter.submitList(item.content)
        }
    }
}