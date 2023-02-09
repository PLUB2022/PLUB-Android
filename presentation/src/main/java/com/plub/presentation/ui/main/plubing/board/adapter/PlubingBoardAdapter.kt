package com.plub.presentation.ui.main.plubing.board.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.plub.domain.model.enums.PlubingBoardType
import com.plub.domain.model.vo.board.PlubingBoardVo
import com.plub.presentation.databinding.IncludeItemBoardClipBoardBinding
import com.plub.presentation.databinding.IncludeItemBoardNormalBinding
import com.plub.presentation.databinding.IncludeItemBoardPinBinding
import com.plub.presentation.databinding.IncludeItemBoardSystemBinding

class PlubingBoardAdapter(
    private val listener: Delegate,
) : ListAdapter<PlubingBoardVo, RecyclerView.ViewHolder>(PlubCardDiffCallback()) {

    companion object {
        private const val NOT_FOUND_INDEX = -1
    }

    interface Delegate {
        fun onClickClipBoard()
        fun onLongClickNormalBoard(id:Int, isHost:Boolean, isAuthor:Boolean)
        fun onClickNormalBoard(id:Int, isHost:Boolean, isAuthor:Boolean)
        val clipBoardList:List<PlubingBoardVo>
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is PlubingBoardNormalViewHolder -> holder.bind(currentList[position])
            is PlubingBoardPinViewHolder -> holder.bind(currentList[position])
            is PlubingBoardSystemViewHolder -> holder.bind(currentList[position])
            is PlubingBoardClipBoardViewHolder -> holder.bind(currentList[position])
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (PlubingBoardType.indexOf(viewType)) {
            PlubingBoardType.NORMAL -> {
                val binding = IncludeItemBoardNormalBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                PlubingBoardNormalViewHolder(binding, listener)
            }
            PlubingBoardType.PIN -> {
                val binding = IncludeItemBoardPinBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                PlubingBoardPinViewHolder(binding, listener)
            }
            PlubingBoardType.SYSTEM -> {
                val binding = IncludeItemBoardSystemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                PlubingBoardSystemViewHolder(binding, listener)
            }
            PlubingBoardType.CLIP_BOARD -> {
                val binding = IncludeItemBoardClipBoardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                PlubingBoardClipBoardViewHolder(binding, listener)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return currentList[position].viewType.idx
    }

    fun notifyClipBoard() {
        val idx = currentList.indexOfFirst {
            it.viewType == PlubingBoardType.CLIP_BOARD
        }
        if(idx != NOT_FOUND_INDEX) notifyItemChanged(idx)
    }
}

class PlubCardDiffCallback : DiffUtil.ItemCallback<PlubingBoardVo>() {
    override fun areItemsTheSame(oldItem: PlubingBoardVo, newItem: PlubingBoardVo): Boolean =
        oldItem.feedId == newItem.feedId && oldItem.viewType == newItem.viewType

    override fun areContentsTheSame(oldItem: PlubingBoardVo, newItem: PlubingBoardVo): Boolean =
        oldItem == newItem
}