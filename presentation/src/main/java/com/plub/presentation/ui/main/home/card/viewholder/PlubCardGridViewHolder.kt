package com.plub.presentation.ui.main.home.card.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.plub.domain.model.vo.plub.PlubCardVo
import com.plub.presentation.R
import com.plub.presentation.databinding.IncludeItemPlubCardGridBinding
import com.plub.presentation.ui.main.home.card.adapter.PlubCardAdapter
import com.plub.presentation.util.GlideUtil
import com.plub.presentation.util.TimeFormatter
import com.plub.presentation.util.onThrottleClick

class PlubCardGridViewHolder(
    private val binding: IncludeItemPlubCardGridBinding,
    private val listener: PlubCardAdapter.Delegate
) : RecyclerView.ViewHolder(binding.root) {

    private var vo: PlubCardVo? = null

    init {
        binding.imageViewBookmark.onThrottleClick {
            vo?.let {
                listener.onClickBookmark(it.id)
            }
        }

        binding.root.onThrottleClick {
            vo?.let {
                listener.onClickPlubCard(it.id)
            }
        }
    }

    fun bind(item: PlubCardVo) {
        vo = item
        binding.apply {
            val time = TimeFormatter.getAmPmHourMin(item.time)
            val memberCount = root.context.getString(R.string.plub_recruit_count, item.remainMemberNumber)
            textViewLocation.text = item.place.ifEmpty { root.context.getString(R.string.word_online) }
            textViewMeetingDate.text = time
            textViewRecruitMemberCount.text = memberCount
            textViewName.text = item.title
            val bookmarkRes = if (item.isBookmarked) R.drawable.ic_bookmark_checked else R.drawable.ic_bookmark_unchecked
            imageViewBookmark.setImageResource(bookmarkRes)
            GlideUtil.loadImage(root.context, item.photo, imageViewBackground)
            imageViewBackground.clipToOutline = true
        }
    }
}