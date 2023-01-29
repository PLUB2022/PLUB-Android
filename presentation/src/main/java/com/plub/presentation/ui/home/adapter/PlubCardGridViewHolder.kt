package com.plub.presentation.ui.home.adapter

import androidx.recyclerview.widget.RecyclerView
import com.plub.domain.model.vo.plub.PlubCardVo
import com.plub.presentation.R
import com.plub.presentation.databinding.IncludeItemPlubCardGridBinding
import com.plub.presentation.util.TimeFormatter

class PlubCardGridViewHolder(
    private val binding: IncludeItemPlubCardGridBinding,
    private val listener: PlubCardAdapter.Delegate
) : RecyclerView.ViewHolder(binding.root) {

    private var vo: PlubCardVo? = null
    private var isBookmarked : Boolean = false

    init {
        binding.imageViewBookmark.setOnClickListener {
            vo?.let {
                listener.onClickBookmark(it.id)
            }
            isBookmarked = !isBookmarked
            val bookmarkRes =
                if (isBookmarked) R.drawable.ic_bookmark_checked else R.drawable.ic_bookmark_unchecked
            binding.imageViewBookmark.setImageResource(bookmarkRes)
        }

        binding.root.setOnClickListener {
            vo?.let {
                listener.onClickPlubCard(it.id)
            }
        }
    }

    fun bind(item: PlubCardVo) {
        vo = item
        isBookmarked = item.isBookmarked
        binding.apply {
            val time = TimeFormatter.getAmPmHourMin(item.time)
            val memberCount = root.context.getString(R.string.plub_recruit_count, item.remainMemberNumber)
            textViewLocation.text = item.place
            textViewMeetingDate.text = time
            textViewRecruitMemberCount.text = memberCount
            textViewName.text = item.title
            val bookmarkRes =
                if (isBookmarked) R.drawable.ic_bookmark_checked else R.drawable.ic_bookmark_unchecked
            imageViewBookmark.setImageResource(bookmarkRes)
//            GlideUtil.loadImage(root.context, item.photo, imageViewBackground)
        }
    }
}