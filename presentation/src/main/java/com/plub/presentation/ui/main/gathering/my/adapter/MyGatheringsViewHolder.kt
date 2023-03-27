package com.plub.presentation.ui.main.gathering.my.adapter

import androidx.recyclerview.widget.RecyclerView
import com.plub.domain.model.vo.myGathering.MyGatheringResponseVo
import com.plub.presentation.databinding.LayoutRecyclerMyGatheringContentBinding
import com.plub.presentation.util.onThrottleClick

class MyGatheringsViewHolder(
    private val binding: LayoutRecyclerMyGatheringContentBinding,
    private val onMyGatheringMeatBallClick: () -> Unit = { },
    private val onMyHostingMeatBallClick: () -> Unit = { },
) : RecyclerView.ViewHolder(binding.root) {
    init {
        binding.imageViewMeatBall.onThrottleClick {
            onMyGatheringMeatBallClick()
            onMyHostingMeatBallClick()
        }
    }

    fun bind(item: MyGatheringResponseVo) {

        binding.executePendingBindings()
    }
}