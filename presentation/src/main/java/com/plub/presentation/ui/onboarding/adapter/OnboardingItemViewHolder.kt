package com.plub.presentation.ui.onboarding.adapter

import androidx.recyclerview.widget.RecyclerView
import com.plub.domain.model.vo.onboarding.OnboardingItemVo
import com.plub.presentation.databinding.IncludeItemOnboardingBinding

class OnboardingItemViewHolder(
    private val binding: IncludeItemOnboardingBinding,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: OnboardingItemVo) {
        binding.apply {
            textViewTitle.text = item.title
            textViewContent.text = item.content
            lottieThumbnail.setAnimation(item.lottieRes)
        }
    }
}