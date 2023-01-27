package com.plub.presentation.ui.home.plubing.main.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.plub.domain.model.vo.plub.PlubCardVo
import com.plub.presentation.R
import com.plub.presentation.databinding.IncludeItemPlubCardListBinding
import com.plub.presentation.ui.home.plubing.main.adapter.MainRecommendGatheringAdapter
import com.plub.presentation.util.TimeFormatter

class MainRecommendListViewHolder(
    private val binding: IncludeItemPlubCardListBinding,
    private val listener : MainRecommendGatheringAdapter.MainRecommendGatheringDelegate
): RecyclerView.ViewHolder(binding.root){
    private var bookmarkFlag = false
    private var plubbingId = 0

    companion object {
        private const val SEPARATOR_OF_DAY = ","
    }

    init {
        binding.apply {
            imageViewBackground.setOnClickListener {
                listener.onClickGoRecruitDetail(plubbingId)
            }

            imageViewBookmark.setOnClickListener {
                if (bookmarkFlag) {
                    imageViewBookmark.setImageResource(R.drawable.ic_unchecked_bookmark_white)
                } else {
                    imageViewBookmark.setImageResource(R.drawable.ic_bookmark_checked)
                }
                bookmarkFlag = !bookmarkFlag
                listener.onClickBookmark(plubbingId)
            }
        }
    }

    fun bind(item: PlubCardVo) {
        val days = item.days.joinToString(SEPARATOR_OF_DAY)
        val time = TimeFormatter.getAmPmHourMin(item.time)
        bookmarkFlag = item.isBookmarked
        plubbingId = item.id
        binding.apply {
            //GlideUtil.loadImage(root.context, item.mainImage, imageViewMeet)
            if(bookmarkFlag){
                imageViewBookmark.setImageResource(R.drawable.ic_bookmark_checked)
            }
            textViewName.text = item.name
            textViewTitle.text = item.title
            textViewLocation.text = item.place
            textViewRecruitMemberCount.text = root.context.getString(R.string.detail_recruitment_people,(item.remainMemberNumber).toString())
            textViewMeetingDate.text = root.context.getString(R.string.word_middle_line, days, time)
        }
    }
}