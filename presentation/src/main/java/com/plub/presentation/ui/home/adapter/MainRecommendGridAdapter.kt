package com.plub.presentation.ui.home.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.plub.domain.model.vo.home.GatheringItemVo
import com.plub.presentation.databinding.LayoutRecyclerRecommendGatheringGridItemBinding
import com.plub.presentation.databinding.LayoutRecyclerRecommendGatheringListItemBinding
import com.plub.presentation.ui.home.adapter.viewholder.MainRecommendGridViewHodler
import com.plub.presentation.ui.home.adapter.viewholder.MainRecommentViewHolder


class MainRecommendGridAdapter() : ListAdapter<GatheringItemVo, RecyclerView.ViewHolder>(
    MainGatheringGridDiffCallBack()
){

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is MainRecommendGridViewHodler -> holder.bind(currentList[position])
        }
        holder.itemView.setOnClickListener {
            Log.d("어뎁터에서 클릭", currentList[position].title)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = LayoutRecyclerRecommendGatheringGridItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MainRecommendGridViewHodler(binding)
    }

}

class MainGatheringGridDiffCallBack : DiffUtil.ItemCallback<GatheringItemVo>() {
    override fun areItemsTheSame(oldItem: GatheringItemVo, newItem: GatheringItemVo): Boolean = oldItem == newItem
    override fun areContentsTheSame(oldItem: GatheringItemVo, newItem: GatheringItemVo): Boolean = oldItem == newItem
}
