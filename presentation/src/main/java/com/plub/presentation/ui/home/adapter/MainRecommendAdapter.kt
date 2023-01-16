package com.plub.presentation.ui.home.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.plub.domain.model.vo.home.GatheringItemVo
import com.plub.domain.model.vo.home.recommendationgatheringvo.RecommendationGatheringDataResponseVo
import com.plub.domain.model.vo.home.recommendationgatheringvo.RecommendationGatheringResponseContentListVo
import com.plub.presentation.databinding.IncludeItemRecommendGatheringListItemBinding
import com.plub.presentation.ui.home.adapter.viewholder.MainRecommendListViewHolder
import com.plub.presentation.ui.home.adapter.viewholder.MainRecommendViewHolder
import com.plub.presentation.ui.home.plubing.categoryChoice.CategoryChoiceViewModel


class MainRecommendAdapter(private val listener: MainRecommendGatheringAdapter.Delegate) : ListAdapter<RecommendationGatheringResponseContentListVo, RecyclerView.ViewHolder>(
    MainGatheringDiffCallBack()
){
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is MainRecommendListViewHolder -> holder.bind(currentList[position])
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = IncludeItemRecommendGatheringListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MainRecommendListViewHolder(binding)
    }


}

class MainGatheringDiffCallBack : DiffUtil.ItemCallback<RecommendationGatheringResponseContentListVo>() {
    override fun areItemsTheSame(oldItem: RecommendationGatheringResponseContentListVo, newItem: RecommendationGatheringResponseContentListVo): Boolean = oldItem == newItem
    override fun areContentsTheSame(oldItem: RecommendationGatheringResponseContentListVo, newItem: RecommendationGatheringResponseContentListVo): Boolean = oldItem == newItem
}
