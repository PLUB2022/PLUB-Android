package com.plub.presentation.ui.main.plubing.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.plub.domain.model.vo.plub.PlubingMemberInfoVo
import com.plub.presentation.databinding.IncludeItemMemberBinding

class PlubingMemberAdapter(
    private val listener: Delegate,
) : ListAdapter<PlubingMemberInfoVo, RecyclerView.ViewHolder>(PlubCardDiffCallback()) {

    interface Delegate {
        fun onClickProfile(id:Int)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is PlubingMemberViewHolder -> holder.bind(currentList[position])
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = IncludeItemMemberBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PlubingMemberViewHolder(binding, listener)
    }

}

class PlubCardDiffCallback : DiffUtil.ItemCallback<PlubingMemberInfoVo>() {
    override fun areItemsTheSame(oldItem: PlubingMemberInfoVo, newItem: PlubingMemberInfoVo): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: PlubingMemberInfoVo, newItem: PlubingMemberInfoVo): Boolean =
        oldItem == newItem
}