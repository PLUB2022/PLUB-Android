package com.plub.presentation.ui.home.adapter.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.plub.domain.model.vo.home.recommendationgatheringvo.RecommendationGatheringResponseContentListVo
import com.plub.presentation.R
import com.plub.presentation.databinding.IncludeItemRecommendGatheringGridBinding
import com.plub.presentation.ui.home.adapter.MainRecommendGridAdapter
import com.plub.presentation.util.GlideUtil

class MainRecommendGridViewHolder(
    private val binding: IncludeItemRecommendGatheringGridBinding,
    private val listener : MainRecommendGridAdapter.MainRecommendGridDelegate
): RecyclerView.ViewHolder(binding.root){

    fun bind(item: RecommendationGatheringResponseContentListVo) {
        binding.apply {
            imageViewRecommendGrid.setOnClickListener {
                listener.onClick(item.plubbingId)
            }

            GlideUtil.loadImage(root.context, item.mainImage, imageViewRecommendGrid)
            if(item.isBookmarked){
                imageBtnBookmark.setImageResource(R.drawable.ic_bookmark_checked)
            }
            textViewPlubbingGatheringName.text = item.title
            //textViewLocation.text =
            //textViewPeople.text = "모집 인원 ${item.maxAccountNum}"
            //textViewDate.text = "매주 ~
        }
    }
}