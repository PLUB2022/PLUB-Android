package com.plub.presentation.ui.main.profile.bottomsheet.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.plub.domain.model.enums.OtherProfileBottomSheetViewType
import com.plub.domain.model.vo.myPage.OtherProfileVo
import com.plub.presentation.databinding.*

class OtherProfileAdapter: ListAdapter<OtherProfileVo, RecyclerView.ViewHolder>(
    OtherProfileDiffCallback()
) {

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is OtherProfileDataViewHolder -> holder.bind(currentList[position].info)
            is OtherProfileTodoViewHolder -> holder.bind(currentList[position].todoList)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(OtherProfileBottomSheetViewType.valueOf(viewType)){
            OtherProfileBottomSheetViewType.PROFILE -> {
                val binding = IncludeItemOtherProfileBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                OtherProfileDataViewHolder(binding)
            }
            OtherProfileBottomSheetViewType.TODO -> {
                val binding = IncludeItemOtherProfileTodoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                OtherProfileTodoViewHolder(binding)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return currentList[position].viewType.type
    }

}

class OtherProfileDiffCallback : DiffUtil.ItemCallback<OtherProfileVo>() {
    override fun areItemsTheSame(oldItem: OtherProfileVo, newItem: OtherProfileVo): Boolean =
        oldItem.viewType == newItem.viewType

    override fun areContentsTheSame(oldItem: OtherProfileVo, newItem: OtherProfileVo): Boolean =
        oldItem == newItem
}