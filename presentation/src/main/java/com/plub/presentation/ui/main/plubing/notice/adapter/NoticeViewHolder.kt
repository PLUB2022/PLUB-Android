package com.plub.presentation.ui.main.plubing.notice.adapter

import androidx.recyclerview.widget.RecyclerView
import com.plub.domain.model.vo.notice.NoticeVo
import com.plub.presentation.R
import com.plub.presentation.databinding.IncludeItemNoticeBinding
import com.plub.presentation.util.onThrottleClick

class NoticeViewHolder(
    private val binding: IncludeItemNoticeBinding,
    private val listener: NoticeAdapter.Delegate
) : RecyclerView.ViewHolder(binding.root) {

    companion object{
        const val SPLIT_DATE = 0
    }
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
        val createdDay = item.createAt.split(" ")[SPLIT_DATE]
        binding.apply {
            textViewTitle.text = root.context.getString(R.string.notice_title, item.title)
            textViewDate.text = createdDay
            textViewContent.text = item.content
        }
    }
}