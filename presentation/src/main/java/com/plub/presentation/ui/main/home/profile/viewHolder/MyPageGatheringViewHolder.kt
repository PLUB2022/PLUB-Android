package com.plub.presentation.ui.main.home.profile.viewHolder

import androidx.recyclerview.widget.RecyclerView
import com.plub.domain.model.enums.MyPageGatheringStateType
import com.plub.domain.model.vo.myPage.MyPageGatheringDetailVo
import com.plub.presentation.databinding.IncludeItemMyGatheringDetailBinding
import com.plub.presentation.ui.main.home.profile.adapter.MyPageParentGatheringAdapter

class MyPageGatheringViewHolder(
    private val binding: IncludeItemMyGatheringDetailBinding,
    private val listener: MyPageParentGatheringAdapter.MyPageDelegate
) : RecyclerView.ViewHolder(binding.root) {

    var gatheringType : MyPageGatheringStateType? = null

    init {
        binding.root.setOnClickListener {
            gatheringType?.let { listener.onClickGathering(it) }
        }
    }

    fun bind(item: MyPageGatheringDetailVo) {
        //gatheringType = item.gatheringType
        binding.apply {
            textViewGatheringName.text = item.title
            textViewGatheringGoal.text = item.goal
        }
    }
}