package com.plub.presentation.ui.home.plubing.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.plub.domain.model.enums.MainHasDataType
import com.plub.domain.model.vo.home.recommendationgatheringvo.RecommendationGatheringResponseVo
import com.plub.domain.model.vo.plub.PlubCardListVo
import com.plub.domain.model.vo.plub.PlubCardVo
import com.plub.presentation.databinding.IncludeItemLayoutMainRecommendGatheringBinding
import com.plub.presentation.databinding.IncludeItemLayoutMainRecommendGatheringNoChoiceBinding
import com.plub.presentation.databinding.IncludeItemMainRecommendFirstBinding
import com.plub.presentation.ui.home.plubing.main.viewholder.MainRecommendFirstViewHolder
import com.plub.presentation.ui.home.plubing.main.viewholder.MainRecommendViewHolder
import com.plub.presentation.ui.home.plubing.main.viewholder.MainRecommendXViewHolder


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
            is MainRecommendViewHolder -> holder.bind(currentList[position])
            is MainRecommendXViewHolder -> holder.bind()
            is MainRecommendFirstViewHolder -> holder.bind()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when(MainHasDataType.valueOf(viewType)){
            MainHasDataType.NO_DATA -> {
                val binding = IncludeItemLayoutMainRecommendGatheringNoChoiceBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                return MainRecommendXViewHolder(binding, listener)
            }
            MainHasDataType.DATA -> {
                val binding = IncludeItemLayoutMainRecommendGatheringBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                return MainRecommendViewHolder(binding, listener)
            }
            MainHasDataType.FIRST -> {
                val binding = IncludeItemMainRecommendFirstBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                return MainRecommendFirstViewHolder(binding, listener)
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