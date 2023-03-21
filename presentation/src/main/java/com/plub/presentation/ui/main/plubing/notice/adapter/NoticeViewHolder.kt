package com.plub.presentation.ui.main.plubing.notice.adapter

import androidx.recyclerview.widget.RecyclerView
import com.plub.domain.model.vo.notice.NoticeVo
import com.plub.presentation.databinding.IncludeItemNoticeBinding
import com.plub.presentation.util.onThrottleClick

class NoticeViewHolder(
    private val binding: IncludeItemNoticeBinding,
    private val listener: NoticeAdapter.Delegate
) : RecyclerView.ViewHolder(binding.root) {

    private var vo: NoticeVo? = null

    init {
        binding.root.onThrottleClick {
            vo?.let {
                listener.onClickNotice(it.noticeId)
            }
        }

        binding.root.setOnLongClickListener {
            vo?.let {
                listener.onLongClickNotice(it)
            }
            true
        }
    }

    fun bind(item: NoticeVo) {
        vo = item
        binding.apply {
            textViewTitle.text = item.title
            textViewDate.text = item.createAt
            textViewContent.text = item.content
        }
    }
}