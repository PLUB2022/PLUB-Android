package com.plub.presentation.ui.main.gathering.my.adapter

import androidx.recyclerview.widget.RecyclerView
import com.plub.presentation.databinding.LayoutRecyclerMyGatheringParticipateBinding
import com.plub.presentation.util.onThrottleClick

class ParticipateGatheringViewHolder(
    private val binding: LayoutRecyclerMyGatheringParticipateBinding,
    private val onParticipateGatheringClick: () -> Unit = { },
) :
    RecyclerView.ViewHolder(binding.root) {
    init {
        binding.root.onThrottleClick {
            onParticipateGatheringClick()
        }
    }
}