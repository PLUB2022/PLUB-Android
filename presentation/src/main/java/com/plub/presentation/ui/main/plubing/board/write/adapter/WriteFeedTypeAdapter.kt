package com.plub.presentation.ui.main.plubing.board.write.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.plub.domain.model.enums.PlubingFeedType
import com.plub.domain.model.vo.board.WriteBoardFeedTypeVo
import com.plub.presentation.databinding.IncludeItemWriteFeedTypeBinding

class WriteFeedTypeAdapter(
    val listener: Delegate
) : ListAdapter<WriteBoardFeedTypeVo, RecyclerView.ViewHolder>(SubHobbyDiffCallback()) {

    interface Delegate {
        fun onClickFeedType(feedType: PlubingFeedType)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is WriteFeedTypeViewHolder -> holder.bind(currentList[position])
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = IncludeItemWriteFeedTypeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return WriteFeedTypeViewHolder(binding, listener)
    }
}

class SubHobbyDiffCallback : DiffUtil.ItemCallback<WriteBoardFeedTypeVo>() {
    override fun areItemsTheSame(oldItem: WriteBoardFeedTypeVo, newItem: WriteBoardFeedTypeVo): Boolean =
        oldItem.feedType == newItem.feedType

    override fun areContentsTheSame(oldItem: WriteBoardFeedTypeVo, newItem: WriteBoardFeedTypeVo): Boolean =
        oldItem == newItem
}