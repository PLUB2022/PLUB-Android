package com.plub.presentation.ui.main.plubing.notice.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.plub.domain.model.enums.NoticeType
import com.plub.domain.model.enums.TodoTimelineViewType
import com.plub.domain.model.vo.notice.NoticeVo
import com.plub.presentation.databinding.IncludeItemNoticeBinding
import com.plub.presentation.databinding.IncludeItemProgressBarBinding
import com.plub.presentation.ui.main.home.progress.LoadingViewHolder

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
            is LoadingViewHolder -> holder.bind()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (NoticeType.valueOf(viewType)) {
            NoticeType.LOADING -> {
                val binding = IncludeItemProgressBarBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                LoadingViewHolder(binding)
            }
            else -> {
                val binding = IncludeItemNoticeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                NoticeViewHolder(binding, listener)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return currentList[position].noticeType.type
    }
}

class NoticeDiffCallback : DiffUtil.ItemCallback<NoticeVo>() {
    override fun areItemsTheSame(oldItem: NoticeVo, newItem: NoticeVo): Boolean =
        oldItem.noticeId == newItem.noticeId

    override fun areContentsTheSame(oldItem: NoticeVo, newItem: NoticeVo): Boolean =
        oldItem == newItem
}