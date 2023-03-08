package com.plub.presentation.ui.main.home.profile.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.plub.domain.model.enums.MyPageDetailViewType
import com.plub.domain.model.vo.home.myPage.MyPageDetailVo
import com.plub.presentation.databinding.*
import com.plub.presentation.ui.main.home.profile.viewHolder.detail.*

class MyPageDetailPageAdapter(): ListAdapter<MyPageDetailVo, RecyclerView.ViewHolder>(
    MyPageDetailDiffCallback()
) {

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is MyPageDetailTopViewHolder -> holder.bind(currentList[position].title)
            is MyPageDetailMyApplicationsViewHolder -> holder.bind()
            is MyPageDetailOtherApplicationsViewHolder -> holder.bind()
            is MyPageDetailApplicationViewHolder -> holder.bind(currentList[position].application)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(MyPageDetailViewType.valueOf(viewType)){
            MyPageDetailViewType.TOP -> {
                val binding = IncludeItemMyPageTopBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                return MyPageDetailTopViewHolder(binding)
            }
            MyPageDetailViewType.MY_APPLICANTS -> {
                val binding = IncludeItemMyPageMyApplicationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                return MyPageDetailMyApplicationsViewHolder(binding)
            }
            MyPageDetailViewType.OTHER_APPLICANTS -> {
                val binding = IncludeItemMyPageOtherApplicationsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                return MyPageDetailOtherApplicationsViewHolder(binding)
            }
            MyPageDetailViewType.APPLICANTS -> {
                val binding = IncludeItemApplicationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                return MyPageDetailApplicationViewHolder(binding)
            }
            MyPageDetailViewType.EMPTY -> {
                val binding = IncludeItemMyPageNoOtherApplicationsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                return MyPageDetailEmptyViewHolder(binding)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return currentList[position].viewType.type
    }
}

class MyPageDetailDiffCallback : DiffUtil.ItemCallback<MyPageDetailVo>() {
    override fun areItemsTheSame(oldItem: MyPageDetailVo, newItem: MyPageDetailVo): Boolean =
        oldItem.viewType == newItem.viewType

    override fun areContentsTheSame(oldItem: MyPageDetailVo, newItem: MyPageDetailVo): Boolean =
        oldItem == newItem
}