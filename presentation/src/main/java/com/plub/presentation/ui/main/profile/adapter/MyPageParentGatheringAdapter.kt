package com.plub.presentation.ui.main.profile.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.plub.domain.model.vo.myPage.MyPageGatheringVo
import com.plub.presentation.databinding.IncludeItemMyGatheringBinding

class MyPageParentGatheringAdapter: ListAdapter<MyPageGatheringVo, RecyclerView.ViewHolder>(MyPageGatheringDiffCallback()) {

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is MyPageParentGatheringViewHolder -> holder.bind(currentList[position])
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = IncludeItemMyGatheringBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyPageParentGatheringViewHolder(binding)
    }

}

class MyPageGatheringDiffCallback : DiffUtil.ItemCallback<MyPageGatheringVo>() {
    override fun areItemsTheSame(oldItem: MyPageGatheringVo, newItem: MyPageGatheringVo): Boolean =
        oldItem.gatheringType == newItem.gatheringType

    override fun areContentsTheSame(oldItem: MyPageGatheringVo, newItem: MyPageGatheringVo): Boolean =
        oldItem == newItem
}