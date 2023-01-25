package com.plub.presentation.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.plub.domain.model.vo.home.recommendationgatheringvo.RecommendationGatheringDataResponseVo
import com.plub.presentation.databinding.IncludeItemRecommendGatheringGridBinding
import com.plub.presentation.ui.home.adapter.viewholder.MainRecommendGridViewHolder


class MainRecommendGridAdapter() : ListAdapter<RecommendationGatheringDataResponseVo, RecyclerView.ViewHolder>(
    MainGatheringGridDiffCallBack()
){

    interface MainRecommendGridDelegate {
//        val categoryList:List<CategoriesDataResponseVo>
//        fun onClickExpand(hobbyId: Int)
//        fun onClickSubHobby(isClicked: Boolean, selectedHobbyVo: SelectedHobbyVo)
//        fun onClickLatePick()
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is MainRecommendGridViewHolder -> holder.bind(currentList[position])
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = IncludeItemRecommendGatheringGridBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MainRecommendGridViewHolder(binding)
    }

}

class MainGatheringGridDiffCallBack : DiffUtil.ItemCallback<RecommendationGatheringDataResponseVo>() {
    override fun areItemsTheSame(oldItem: RecommendationGatheringDataResponseVo, newItem: RecommendationGatheringDataResponseVo): Boolean = oldItem == newItem
    override fun areContentsTheSame(oldItem: RecommendationGatheringDataResponseVo, newItem: RecommendationGatheringDataResponseVo): Boolean = oldItem == newItem
}