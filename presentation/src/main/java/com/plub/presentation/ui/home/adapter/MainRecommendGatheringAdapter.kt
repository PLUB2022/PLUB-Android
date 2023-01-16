package com.plub.presentation.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.plub.domain.model.vo.home.GatheringItemVo
import com.plub.domain.model.vo.home.categorylistresponsevo.CategoriesDataResponseVo
import com.plub.domain.model.vo.home.recommendationgatheringvo.RecommendationGatheringDataResponseVo
import com.plub.presentation.databinding.IncludeItemLayoutMainRecommendGatheringBinding
import com.plub.presentation.databinding.IncludeItemRecommendGatheringListItemBinding
import com.plub.presentation.ui.home.adapter.viewholder.MainRecommendViewHolder
import com.plub.presentation.ui.sign.hobbies.adapter.HobbiesAdapter


class MainRecommendGatheringAdapter(private val listener: MainRecommendGatheringDelegate) : ListAdapter<RecommendationGatheringDataResponseVo, RecyclerView.ViewHolder>(
    MainRecommendGatheringDiffCallBack()
){
    private val subListenerList: MutableSet<HobbiesAdapter.SubListener> = mutableSetOf()

    interface MainRecommendGatheringDelegate {
//        val categoryList:List<CategoriesDataResponseVo>
//        fun onClickExpand(hobbyId: Int)
//        fun onClickSubHobby(isClicked: Boolean, selectedHobbyVo: SelectedHobbyVo)
//        fun onClickLatePick()
    }

    interface SubListener {
        fun onNotifySubItemChange(parentId: Int, subId: Int)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is MainRecommendViewHolder -> holder.bind(currentList[position])
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = IncludeItemLayoutMainRecommendGatheringBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MainRecommendViewHolder(binding, listener)
    }

}

class MainRecommendGatheringDiffCallBack : DiffUtil.ItemCallback<RecommendationGatheringDataResponseVo>() {
    override fun areItemsTheSame(oldItem: RecommendationGatheringDataResponseVo, newItem: RecommendationGatheringDataResponseVo): Boolean = oldItem == newItem
    override fun areContentsTheSame(oldItem: RecommendationGatheringDataResponseVo, newItem: RecommendationGatheringDataResponseVo): Boolean = oldItem == newItem
}