package com.plub.presentation.ui.main.profile.viewHolder

import androidx.recyclerview.widget.RecyclerView
import com.plub.presentation.databinding.IncludeMyPgaeEmptyBinding
import com.plub.presentation.ui.main.profile.adapter.MyPageParentGatheringAdapter
import com.plub.presentation.util.onThrottleClick

class MyPageEmptyViewHolder(
    private val binding: IncludeMyPgaeEmptyBinding,
    private val listener: MyPageParentGatheringAdapter.MyPageDelegate
) : RecyclerView.ViewHolder(binding.root) {

    init {
        binding.apply {
            buttonSeeOtherGathering.onThrottleClick {
                listener.onClickGoToHome()
            }
        }
    }

    fun bind() {

    }
}