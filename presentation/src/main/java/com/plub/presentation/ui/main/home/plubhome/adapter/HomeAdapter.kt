package com.plub.presentation.ui.main.home.plubhome.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.plub.domain.model.enums.HomeViewType
import com.plub.domain.model.vo.home.HomePlubListVo
import com.plub.presentation.databinding.*
import com.plub.presentation.ui.main.home.plubhome.viewholder.*
import com.plub.presentation.ui.main.home.progress.LoadingViewHolder
import com.plub.presentation.ui.main.plubing.schedule.adapter.scheduleCard.PlubingScheduleLoadingViewHolder
import com.plub.presentation.util.PlubLogger

class HomeAdapter(private val listener: HomeDelegate) : ListAdapter<HomePlubListVo, RecyclerView.ViewHolder>(
    HomeItemDiffCallBack()
){

    interface HomeDelegate {
        fun onCategoryClick(categoryId: Int, categoryName : String)
        fun onClickGoRecruitDetail(plubbingId : Int)
        fun onClickBookmark(plubbingId: Int)
        fun onClickRegister()
        fun onClickSetting()
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is HomeCategoryParentViewHolder -> holder.bind(currentList[position].categoryList)
            is HomeRecommendTitleViewHolder -> holder.bind()
            is HomeNoInterestViewHolder -> holder.bind()
            is HomeRecommendListViewHolder -> holder.bind(currentList[position].recommendGathering)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(HomeViewType.valueOf(viewType)){
            HomeViewType.TITLE_VIEW -> {
                val binding = IncludeItemHomeTitleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                HomeTitleViewHolder(binding)
            }
            HomeViewType.CATEGORY_VIEW -> {
                val binding = IncludeItemHomeCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                HomeCategoryParentViewHolder(binding, listener)
            }
            HomeViewType.RECOMMEND_TITLE_VIEW -> {
                val binding = IncludeItemHomeRecommendFirstBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                HomeRecommendTitleViewHolder(binding, listener)
            }
            HomeViewType.REGISTER_HOBBIES_VIEW -> {
                val binding = IncludeItemLayoutHomeRecommendGatheringNoChoiceBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                HomeNoInterestViewHolder(binding, listener)
            }
            HomeViewType.RECOMMEND_GATHERING_VIEW -> {
                val binding = IncludeItemPlubCardListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                HomeRecommendListViewHolder(binding, listener)
            }
            HomeViewType.LOADING -> {
                val binding = LayoutRecyclerPlubingScheduleLoadingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                PlubingScheduleLoadingViewHolder(binding)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return currentList[position].viewType.type
    }
}

class HomeItemDiffCallBack : DiffUtil.ItemCallback<HomePlubListVo>() {
    override fun areItemsTheSame(oldItem: HomePlubListVo, newItem: HomePlubListVo): Boolean =
        oldItem.viewType == newItem.viewType && oldItem.recommendGathering.id == newItem.recommendGathering.id
    override fun areContentsTheSame(oldItem: HomePlubListVo, newItem: HomePlubListVo): Boolean =
        oldItem == newItem
}