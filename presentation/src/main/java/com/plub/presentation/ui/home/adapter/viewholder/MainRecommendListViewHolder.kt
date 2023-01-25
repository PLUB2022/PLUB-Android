package com.plub.presentation.ui.home.adapter.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.plub.domain.model.vo.home.recommendationgatheringvo.RecommendationGatheringResponseContentListVo
import com.plub.presentation.R
import com.plub.presentation.databinding.IncludeItemRecommendGatheringListItemBinding
import com.plub.presentation.ui.home.adapter.MainRecommendGatheringAdapter
import com.plub.presentation.util.GlideUtil

class MainRecommendListViewHolder(
    private val binding: IncludeItemRecommendGatheringListItemBinding,
    private val listener : MainRecommendGatheringAdapter.MainRecommendGatheringDelegate
): RecyclerView.ViewHolder(binding.root){

    fun bind(item: RecommendationGatheringResponseContentListVo) {
        binding.apply {
            constraintLayoutItemLayout.setOnClickListener{
                listener.onClick(item.plubbingId)
            }

            GlideUtil.loadImage(root.context, item.mainImage, imageViewMeet)
            if(item.isBookmarked){
                imageBtnBookmark.setImageResource(R.drawable.ic_bookmark_checked)
            }
            textViewMeetTitle.text = item.title
            textViewMeetOnelineIntroduce.text = item.introduce
            //textViewLocation.text =
            //textViewPeople.text = "모집 인원 ${item.maxAccountNum}"
            //textViewDate.text = "매주 ~
        }
    }
}