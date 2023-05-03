package com.plub.presentation.ui.main.plubing.notice.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.plub.domain.model.vo.notice.NoticeVo
import com.plub.presentation.databinding.IncludeItemNoticeBinding

class NoticeAdapter(
    private val listener: Delegate,
) : ListAdapter<NoticeVo, RecyclerView.ViewHolder>(NoticeDiffCallback()) {

    interface Delegate {
        fun onClickNotice(noticeId: Int)
        fun onLongClickNotice(noticeVo:NoticeVo)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is NoticeViewHolder -> holder.bind(currentList[position])
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = IncludeItemNoticeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NoticeViewHolder(binding, listener)
    }
}

class NoticeDiffCallback : DiffUtil.ItemCallback<NoticeVo>() {
    override fun areItemsTheSame(oldItem: NoticeVo, newItem: NoticeVo): Boolean =
        oldItem.noticeId == newItem.noticeId

    override fun areContentsTheSame(oldItem: NoticeVo, newItem: NoticeVo): Boolean =
        oldItem == newItem
}