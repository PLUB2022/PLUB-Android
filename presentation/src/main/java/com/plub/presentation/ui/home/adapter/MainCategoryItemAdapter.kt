package com.plub.presentation.ui.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.plub.domain.model.vo.home.SampleCategoryVo
import com.plub.presentation.R
import com.plub.presentation.databinding.ItemMainCategoryBinding
import com.plub.presentation.ui.home.adapter.viewholder.MainCategoryViewHolder
import com.plub.presentation.ui.sign.onboarding.adapter.OnboardingItemViewHolder


class MainCategoryItemAdapter() : ListAdapter<SampleCategoryVo, RecyclerView.ViewHolder>(
    MainCategoryDiffCallBack()
){
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is MainCategoryViewHolder -> holder.bind(currentList[position])
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = ItemMainCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MainCategoryViewHolder(binding)
    }

}

class MainCategoryDiffCallBack : DiffUtil.ItemCallback<SampleCategoryVo>() {
    override fun areItemsTheSame(oldItem: SampleCategoryVo, newItem: SampleCategoryVo): Boolean = oldItem == newItem
    override fun areContentsTheSame(oldItem: SampleCategoryVo, newItem: SampleCategoryVo): Boolean = oldItem == newItem
}
