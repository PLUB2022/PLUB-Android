package com.plub.presentation.ui.home.adapter.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.plub.domain.model.vo.home.recommendationgatheringvo.RecommendationGatheringResponseContentListVo
import com.plub.presentation.R
import com.plub.presentation.databinding.IncludeItemRecommendGatheringGridBinding
import com.plub.presentation.ui.home.adapter.MainRecommendGridAdapter

class MainRecommendGridViewHolder(
    private val binding: IncludeItemRecommendGatheringGridBinding,
    private val listener : MainRecommendGridAdapter.MainRecommendGridDelegate
): RecyclerView.ViewHolder(binding.root){

    fun bind(item: RecommendationGatheringResponseContentListVo) {
        var bookmarkFlag = item.isBookmarked
        binding.apply {
            imageViewRecommendGrid.setOnClickListener {
                listener.onClickGoRecruitDetail(item.plubbingId)
            }

            imageBtnBookmark.setOnClickListener {
                if(bookmarkFlag){
                    imageBtnBookmark.setImageResource(R.drawable.ic_unchecked_bookmark)
                }
                else{
                    imageBtnBookmark.setImageResource(R.drawable.ic_bookmark_checked)
                }
                bookmarkFlag = !bookmarkFlag
                listener.onClickBookmark(item.plubbingId)
            }


            //GlideUtil.loadImage(root.context, item.mainImage, imageViewRecommendGrid)
            if(bookmarkFlag){
                imageBtnBookmark.setImageResource(R.drawable.ic_bookmark_checked)
            }
            textViewPlubbingGatheringName.text = item.title
            textViewLocation.text = item.roadAddress
            textViewPeople.text = "모집 인원 ${item.curAccountNum + item.remainAccountNum}명"
            textViewDate.text = "${item.days.toString()} | ${item.time}"
        }
    }
}