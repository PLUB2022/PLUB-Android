package com.plub.presentation.ui.main.profile.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.plub.domain.model.enums.MyPageGatheringMyType
import com.plub.domain.model.enums.MyPageGatheringStateType
import com.plub.domain.model.enums.MyPageViewType
import com.plub.domain.model.vo.myPage.MyPageVo
import com.plub.presentation.databinding.*
import com.plub.presentation.ui.main.profile.viewHolder.MyPageEmptyViewHolder
import com.plub.presentation.ui.main.profile.viewHolder.MyPageParentGatheringViewHolder
import com.plub.presentation.ui.main.profile.viewHolder.MyPageProfileViewHolder

class MyPageParentGatheringAdapter(private val listener: MyPageDelegate): ListAdapter<MyPageVo, RecyclerView.ViewHolder>(
    MyPageParentGatheringDiffCallback()
) {

    interface MyPageDelegate{
        fun onClickGathering(gatheringParentType : MyPageGatheringStateType, gatheringType : MyPageGatheringMyType, plubbingId : Int)
        fun onClickEdit()
        fun onClickGoToHome()
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is MyPageParentGatheringViewHolder -> holder.bind(currentList[position].myPageGathering)
            is MyPageProfileViewHolder -> holder.bind(currentList[position].myPageMyProfileVo)
            is MyPageEmptyViewHolder -> holder.bind()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(MyPageViewType.valueOf(viewType)){
            MyPageViewType.GATHERING -> {
                val binding = IncludeItemMyGatheringBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                MyPageParentGatheringViewHolder(binding, listener)
            }
            MyPageViewType.PROFILE -> {
                val binding = IncludeMyPageProfileBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                MyPageProfileViewHolder(binding, listener)
            }
            MyPageViewType.EMPTY -> {
                val binding = IncludeMyPgaeEmptyBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                MyPageEmptyViewHolder(binding, listener)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return currentList[position].myPageType.type
    }
}

class MyPageParentGatheringDiffCallback : DiffUtil.ItemCallback<MyPageVo>() {
    override fun areItemsTheSame(oldItem: MyPageVo, newItem: MyPageVo): Boolean =
        oldItem.myPageType == newItem.myPageType

    override fun areContentsTheSame(oldItem: MyPageVo, newItem: MyPageVo): Boolean =
        oldItem == newItem
}