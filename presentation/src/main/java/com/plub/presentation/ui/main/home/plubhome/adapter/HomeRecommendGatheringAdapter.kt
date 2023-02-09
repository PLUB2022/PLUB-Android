package com.plub.presentation.ui.main.home.plubhome.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.plub.domain.model.enums.PlubHomeRecommendViewType
import com.plub.domain.model.vo.home.recommendationgatheringvo.RecommendationGatheringResponseVo
import com.plub.presentation.databinding.IncludeItemLayoutHomeRecommendGatheringBinding
import com.plub.presentation.databinding.IncludeItemLayoutHomeRecommendGatheringNoChoiceBinding
import com.plub.presentation.databinding.IncludeItemHomeRecommendFirstBinding
import com.plub.presentation.databinding.IncludeItemProgressBarBinding
import com.plub.presentation.ui.main.home.plubhome.HomeFragmentViewModel
import com.plub.presentation.ui.main.home.plubhome.viewholder.HomeRecommendFirstViewHolder
import com.plub.presentation.ui.main.home.plubhome.viewholder.HomeRecommendViewHolder
import com.plub.presentation.ui.main.home.plubhome.viewholder.HomeRecommendXViewHolder
import com.plub.presentation.ui.main.home.progress.LoadingViewHolder


class HomeRecommendGatheringAdapter(private val listener: HomeRecommendGatheringDelegate) : ListAdapter<RecommendationGatheringResponseVo, RecyclerView.ViewHolder>(
    MainRecommendGatheringDiffCallBack()
){
    interface HomeRecommendGatheringDelegate {
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
        return when(PlubHomeRecommendViewType.valueOf(viewType)){
            PlubHomeRecommendViewType.REGISTER_HOBBIES_VIEW -> {
                val binding = IncludeItemLayoutHomeRecommendGatheringNoChoiceBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                HomeRecommendXViewHolder(binding, listener)
            }
            PlubHomeRecommendViewType.RECOMMEND_VIEW -> {
                val binding = IncludeItemLayoutHomeRecommendGatheringBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                HomeRecommendViewHolder(binding, listener)
            }
            PlubHomeRecommendViewType.FIRST_VIEW -> {
                val binding = IncludeItemHomeRecommendFirstBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                HomeRecommendFirstViewHolder(binding, listener)
            }
            PlubHomeRecommendViewType.LOADING -> {
                val binding = IncludeItemProgressBarBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                LoadingViewHolder(binding)
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