package com.plub.presentation.ui.main.home.profile.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.plub.domain.model.vo.myPage.MyPageGatheringDetailVo
import com.plub.presentation.databinding.IncludeItemMyGatheringDetailBinding

class MyPageGatheringAdapter(private val listener: MyPageParentGatheringAdapter.MyPageDelegate): ListAdapter<MyPageGatheringDetailVo, RecyclerView.ViewHolder>(
    MyPageGatheringDiffCallback()
) {

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is MyPageGatheringViewHolder -> holder.bind(currentList[position])
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = IncludeItemMyGatheringDetailBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyPageGatheringViewHolder(binding, listener)
    }

}

class MyPageGatheringDiffCallback : DiffUtil.ItemCallback<MyPageGatheringDetailVo>() {
    override fun areItemsTheSame(oldItem: MyPageGatheringDetailVo, newItem: MyPageGatheringDetailVo): Boolean =
        oldItem.gatheringType == newItem.gatheringType

    override fun areContentsTheSame(oldItem: MyPageGatheringDetailVo, newItem: MyPageGatheringDetailVo): Boolean =
        oldItem == newItem
}