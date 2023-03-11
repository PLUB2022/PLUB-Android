package com.plub.presentation.ui.main.home.profile.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.plub.domain.model.enums.MyPageGatheringMyType
import com.plub.domain.model.enums.MyPageGatheringStateType
import com.plub.domain.model.vo.myPage.MyPageGatheringVo
import com.plub.presentation.databinding.IncludeItemMyGatheringBinding
import com.plub.presentation.ui.main.home.profile.viewHolder.MyPageParentGatheringViewHolder

class MyPageParentGatheringAdapter(private val listener: MyPageDelegate): ListAdapter<MyPageGatheringVo, RecyclerView.ViewHolder>(
    MyPageParentGatheringDiffCallback()
) {

    interface MyPageDelegate{
        fun onClickCardExpand(gatheringType: MyPageGatheringStateType)
        fun onClickGathering(gatheringParentType : MyPageGatheringStateType, gatheringType : MyPageGatheringMyType, plubbingId : Int)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is MyPageParentGatheringViewHolder -> holder.bind(currentList[position])
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = IncludeItemMyGatheringBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyPageParentGatheringViewHolder(binding, listener)
    }

}

class MyPageParentGatheringDiffCallback : DiffUtil.ItemCallback<MyPageGatheringVo>() {
    override fun areItemsTheSame(oldItem: MyPageGatheringVo, newItem: MyPageGatheringVo): Boolean =
        oldItem.gatheringType == newItem.gatheringType

    override fun areContentsTheSame(oldItem: MyPageGatheringVo, newItem: MyPageGatheringVo): Boolean =
        oldItem == newItem
}