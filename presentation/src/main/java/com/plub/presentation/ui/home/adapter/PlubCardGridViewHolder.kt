package com.plub.presentation.ui.home.adapter

import androidx.recyclerview.widget.RecyclerView
import com.plub.domain.model.vo.plub.PlubCardVo
import com.plub.presentation.R
import com.plub.presentation.databinding.IncludeItemPlubCardGridBinding
import com.plub.presentation.util.GlideUtil

class PlubCardGridViewHolder(
    private val binding: IncludeItemPlubCardGridBinding,
    private val listener: PlubCardAdapter.Delegate
) : RecyclerView.ViewHolder(binding.root) {

    private var vo: PlubCardVo? = null

    init {
        binding.root.setOnClickListener {
            vo?.let {
                listener.onClickPlubCard(it.id)
            }
        }
    }

    fun bind(item: PlubCardVo) {
        vo = item
        binding.apply {
            textViewLocation.text = item.place
            textViewMeetingDate.text = item.place
            textViewRecruitMemberCount.text = item.remainMemberNumber.toString()
            textViewName.text = item.title
            val bookmarkRes =
                if (item.isBookmarked) R.drawable.ic_bookmark_checked else R.drawable.ic_bookmark_unchecked
            imageViewBookmark.setImageResource(bookmarkRes)
//            GlideUtil.loadImage(root.context, item.photo, imageViewBackground)
        }
    }
}