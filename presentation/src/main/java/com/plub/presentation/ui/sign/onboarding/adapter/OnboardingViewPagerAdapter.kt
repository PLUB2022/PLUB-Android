package com.plub.presentation.ui.sign.onboarding.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.plub.domain.model.vo.onboarding.OnboardingItemVo
import com.plub.presentation.databinding.ItemOnboardingBinding

class OnboardingViewPagerAdapter() : ListAdapter<OnboardingItemVo, RecyclerView.ViewHolder>(
    OnboardingDiffCallback()
) {

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is OnboardingItemViewHolder -> holder.bind(currentList[position])
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = ItemOnboardingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return OnboardingItemViewHolder(binding)
    }

}

class OnboardingDiffCallback : DiffUtil.ItemCallback<OnboardingItemVo>() {
    override fun areItemsTheSame(oldItem: OnboardingItemVo, newItem: OnboardingItemVo): Boolean = oldItem == newItem
    override fun areContentsTheSame(oldItem: OnboardingItemVo, newItem: OnboardingItemVo): Boolean = oldItem == newItem
}