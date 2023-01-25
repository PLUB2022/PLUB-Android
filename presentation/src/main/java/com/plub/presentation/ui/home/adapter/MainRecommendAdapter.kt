package com.plub.presentation.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.plub.domain.model.vo.home.GatheringItemVo
import com.plub.presentation.databinding.LayoutRecyclerRecommendGatheringListItemBinding
import com.plub.presentation.ui.home.adapter.viewholder.MainRecommendViewHolder


class MainRecommendAdapter() : ListAdapter<GatheringItemVo, RecyclerView.ViewHolder>(
    MainGatheringDiffCallBack()
){
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is MainRecommendViewHolder -> holder.bind(currentList[position])
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = LayoutRecyclerRecommendGatheringListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MainRecommendViewHolder(binding)
    }

}

class MainGatheringDiffCallBack : DiffUtil.ItemCallback<GatheringItemVo>() {
    override fun areItemsTheSame(oldItem: GatheringItemVo, newItem: GatheringItemVo): Boolean = oldItem == newItem
    override fun areContentsTheSame(oldItem: GatheringItemVo, newItem: GatheringItemVo): Boolean = oldItem == newItem
}