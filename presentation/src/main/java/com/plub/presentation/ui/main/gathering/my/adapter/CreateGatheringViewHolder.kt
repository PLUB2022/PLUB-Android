package com.plub.presentation.ui.main.gathering.my.adapter

import androidx.recyclerview.widget.RecyclerView
import com.plub.presentation.databinding.LayoutRecyclerMyGatheringCreateBinding
import com.plub.presentation.util.onThrottleClick

class CreateGatheringViewHolder(
    private val binding: LayoutRecyclerMyGatheringCreateBinding,
    private val onCreateGatheringClick: () -> Unit = { },
) :
    RecyclerView.ViewHolder(binding.root) {
    init {
        binding.root.onThrottleClick {
            onCreateGatheringClick()
        }
    }
}