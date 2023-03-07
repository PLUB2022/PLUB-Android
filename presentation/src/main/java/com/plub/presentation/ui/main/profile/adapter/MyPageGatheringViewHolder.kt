package com.plub.presentation.ui.main.profile.adapter

import androidx.recyclerview.widget.RecyclerView
import com.plub.presentation.databinding.IncludeItemMyGatheringDetailBinding

class MyPageGatheringViewHolder(
    private val binding: IncludeItemMyGatheringDetailBinding,
) : RecyclerView.ViewHolder(binding.root) {

    init {

    }

    fun bind(item: String) {
        binding.apply {
            textViewGatheringName.text = item
            textViewGatheringGoal.text = item
        }
    }
}