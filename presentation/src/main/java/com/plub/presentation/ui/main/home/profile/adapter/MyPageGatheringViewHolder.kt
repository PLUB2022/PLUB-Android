package com.plub.presentation.ui.main.home.profile.adapter

import androidx.recyclerview.widget.RecyclerView
import com.plub.domain.model.enums.MyPageGatheringType
import com.plub.domain.model.vo.myPage.MyPageGatheringDetailVo
import com.plub.presentation.databinding.IncludeItemMyGatheringDetailBinding

class MyPageGatheringViewHolder(
    private val binding: IncludeItemMyGatheringDetailBinding,
    private val listener: MyPageParentGatheringAdapter.MyPageDelegate
) : RecyclerView.ViewHolder(binding.root) {

    var gatheringType : MyPageGatheringType? = null

    init {
        binding.root.setOnClickListener {
            gatheringType?.let { listener.onClickGathering(it) }
        }
    }

    fun bind(item: MyPageGatheringDetailVo) {
        gatheringType = item.gatheringType
        binding.apply {
            textViewGatheringName.text = item.title
            textViewGatheringGoal.text = item.goal
        }
    }
}