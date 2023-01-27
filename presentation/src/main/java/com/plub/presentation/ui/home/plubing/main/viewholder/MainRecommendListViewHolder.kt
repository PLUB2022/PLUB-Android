package com.plub.presentation.ui.home.plubing.main.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.plub.domain.model.vo.home.recommendationgatheringvo.RecommendationGatheringResponseContentListVo
import com.plub.domain.model.vo.plub.PlubCardVo
import com.plub.presentation.R
import com.plub.presentation.databinding.IncludeItemRecommendGatheringListItemBinding
import com.plub.presentation.ui.home.plubing.main.adapter.MainRecommendGatheringAdapter

class MainRecommendListViewHolder(
    private val binding: IncludeItemRecommendGatheringListItemBinding,
    private val listener : MainRecommendGatheringAdapter.MainRecommendGatheringDelegate
): RecyclerView.ViewHolder(binding.root){
    private var bookmarkFlag = false
    private var plubbingId = 0

    init {
        binding.apply {
            constraintLayoutItemLayout.setOnClickListener {
                listener.onClickGoRecruitDetail(plubbingId)
            }

            imageBtnBookmark.setOnClickListener {
                if (bookmarkFlag) {
                    imageBtnBookmark.setImageResource(R.drawable.ic_unchecked_bookmark_white)
                } else {
                    imageBtnBookmark.setImageResource(R.drawable.ic_bookmark_checked)
                }
                bookmarkFlag = !bookmarkFlag
                listener.onClickBookmark(plubbingId)
            }
        }
    }

    fun bind(item: PlubCardVo) {
        bookmarkFlag = item.isBookmarked
        plubbingId = item.id
        binding.apply {
            //GlideUtil.loadImage(root.context, item.mainImage, imageViewMeet)
            if(bookmarkFlag){
                imageBtnBookmark.setImageResource(R.drawable.ic_bookmark_checked)
            }
            textViewMeetTitle.text = item.title
            textViewMeetOnelineIntroduce.text = ""
            textViewLocation.text = item.place
            textViewPeople.text = item.remainMemberNumber.toString()
            textViewDate.text = "${item.days.toString()} | ${item.time}"
        }
    }
}