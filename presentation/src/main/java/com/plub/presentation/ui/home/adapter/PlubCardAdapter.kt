package com.plub.presentation.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.plub.domain.model.enums.PlubCardType
import com.plub.domain.model.vo.plub.PlubCardVo
import com.plub.presentation.databinding.IncludeItemPlubCardGridBinding
import com.plub.presentation.databinding.IncludeItemPlubCardListBinding

class PlubCardAdapter(
    private val listener: Delegate,
) : ListAdapter<PlubCardVo, RecyclerView.ViewHolder>(PlubCardDiffCallback()) {

    interface Delegate {
        fun onClickBookmark(id: Int)
        fun onClickPlubCard(id: Int)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is PlubCardListViewHolder -> holder.bind(currentList[position])
            is PlubCardGridViewHolder -> holder.bind(currentList[position])
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (PlubCardType.valueOf(viewType)) {
            PlubCardType.LIST -> {
                val binding = IncludeItemPlubCardListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                PlubCardListViewHolder(binding, listener)
            }
            PlubCardType.GRID -> {
                val binding = IncludeItemPlubCardGridBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                PlubCardGridViewHolder(binding, listener)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return currentList[position].viewType.type
    }

}

class PlubCardDiffCallback : DiffUtil.ItemCallback<PlubCardVo>() {
    override fun areItemsTheSame(oldItem: PlubCardVo, newItem: PlubCardVo): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: PlubCardVo, newItem: PlubCardVo): Boolean =
        oldItem == newItem
}