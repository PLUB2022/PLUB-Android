package com.plub.presentation.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.plub.domain.model.vo.home.recommendationgatheringvo.RecommendationGatheringResponseContentListVo
import com.plub.presentation.databinding.IncludeItemRecommendGatheringGridBinding
import com.plub.presentation.ui.home.adapter.viewholder.MainRecommendGridViewHolder


class MainRecommendGridAdapter(private val listener: MainRecommendGridAdapter.MainRecommendGridDelegate) : ListAdapter<RecommendationGatheringResponseContentListVo, RecyclerView.ViewHolder>(
    MainGatheringGridDiffCallBack()
){

    interface MainRecommendGridDelegate {
        fun onClickGoRecruitDetail(plubbingId : Int)
        fun onClickBookmark(plubbingId: Int)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is MainRecommendGridViewHolder -> holder.bind(currentList[position])
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = IncludeItemRecommendGatheringGridBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MainRecommendGridViewHolder(binding, listener)
    }

}

class MainGatheringGridDiffCallBack : DiffUtil.ItemCallback<RecommendationGatheringResponseContentListVo>() {
    override fun areItemsTheSame(oldItem: RecommendationGatheringResponseContentListVo, newItem: RecommendationGatheringResponseContentListVo): Boolean = oldItem == newItem
    override fun areContentsTheSame(oldItem: RecommendationGatheringResponseContentListVo, newItem: RecommendationGatheringResponseContentListVo): Boolean = oldItem == newItem
}