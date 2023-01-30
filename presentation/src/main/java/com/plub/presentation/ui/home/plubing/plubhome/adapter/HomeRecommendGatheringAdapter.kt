package com.plub.presentation.ui.home.plubing.plubhome.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.plub.domain.model.enums.MainHasDataType
import com.plub.domain.model.vo.home.recommendationgatheringvo.RecommendationGatheringResponseVo
import com.plub.presentation.databinding.IncludeItemLayoutHomeRecommendGatheringBinding
import com.plub.presentation.databinding.IncludeItemLayoutHomeRecommendGatheringNoChoiceBinding
import com.plub.presentation.databinding.IncludeItemHomeRecommendFirstBinding
import com.plub.presentation.ui.home.plubing.plubhome.viewholder.HomeRecommendFirstViewHolder
import com.plub.presentation.ui.home.plubing.plubhome.viewholder.HomeRecommendViewHolder
import com.plub.presentation.ui.home.plubing.plubhome.viewholder.HomeRecommendXViewHolder


class MainRecommendGatheringAdapter(private val listener: MainRecommendGatheringDelegate) : ListAdapter<RecommendationGatheringResponseVo, RecyclerView.ViewHolder>(
    MainRecommendGatheringDiffCallBack()
){
    interface MainRecommendGatheringDelegate {
        fun onClickGoRecruitDetail(plubbingId : Int)
        fun onClickBookmark(plubbingId: Int)
        fun onClickRegister()
        fun onClickSetting()
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is HomeRecommendViewHolder -> holder.bind(currentList[position])
            is HomeRecommendXViewHolder -> holder.bind()
            is HomeRecommendFirstViewHolder -> holder.bind()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when(MainHasDataType.valueOf(viewType)){
            MainHasDataType.NO_DATA -> {
                val binding = IncludeItemLayoutHomeRecommendGatheringNoChoiceBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                return HomeRecommendXViewHolder(binding, listener)
            }
            MainHasDataType.DATA -> {
                val binding = IncludeItemLayoutHomeRecommendGatheringBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                return HomeRecommendViewHolder(binding, listener)
            }
            MainHasDataType.FIRST -> {
                val binding = IncludeItemHomeRecommendFirstBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                return HomeRecommendFirstViewHolder(binding, listener)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return currentList[position].viewType.type
    }

}

class MainRecommendGatheringDiffCallBack : DiffUtil.ItemCallback<RecommendationGatheringResponseVo>() {
    override fun areItemsTheSame(oldItem: RecommendationGatheringResponseVo, newItem: RecommendationGatheringResponseVo): Boolean = oldItem.content == newItem.content
    override fun areContentsTheSame(oldItem: RecommendationGatheringResponseVo, newItem: RecommendationGatheringResponseVo): Boolean = oldItem == newItem
}