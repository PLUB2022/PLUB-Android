package com.plub.presentation.ui.main.home.plubhome.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.plub.domain.model.vo.plub.PlubCardVo
import com.plub.presentation.databinding.IncludeItemPlubCardListBinding
import com.plub.presentation.ui.main.home.plubhome.viewholder.HomeRecommendListViewHolder


class HomeRecommendAdapter(private val listener: HomeRecommendGatheringAdapter.HomeRecommendGatheringDelegate) : ListAdapter<PlubCardVo, RecyclerView.ViewHolder>(
    MainGatheringDiffCallBack()
){
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is HomeRecommendListViewHolder -> holder.bind(currentList[position])
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = IncludeItemPlubCardListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HomeRecommendListViewHolder(binding, listener)
    }


}

class MainGatheringDiffCallBack : DiffUtil.ItemCallback<PlubCardVo>() {
    override fun areItemsTheSame(oldItem: PlubCardVo, newItem: PlubCardVo): Boolean = oldItem.id == newItem.id
    override fun areContentsTheSame(oldItem: PlubCardVo, newItem: PlubCardVo): Boolean = oldItem == newItem
}