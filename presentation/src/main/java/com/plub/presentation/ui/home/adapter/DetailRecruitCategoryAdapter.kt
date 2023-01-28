package com.plub.presentation.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.plub.presentation.databinding.IncludeItemPlubbingSubHobbyBinding
import com.plub.presentation.ui.home.adapter.viewholder.DetailRecruitCategoryViewHolder

class DetailRecruitCategoryAdapter() : ListAdapter<String, DetailRecruitCategoryViewHolder>(
    DetailRecruitCategoryDiffCallBack()
){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailRecruitCategoryViewHolder {
        val binding = IncludeItemPlubbingSubHobbyBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DetailRecruitCategoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DetailRecruitCategoryViewHolder, position: Int) {
        holder.bind(currentList[position])
    }
}

class DetailRecruitCategoryDiffCallBack : DiffUtil.ItemCallback<String>() {
    override fun areItemsTheSame(oldItem: String, newItem: String): Boolean = oldItem == newItem
    override fun areContentsTheSame(oldItem: String, newItem: String): Boolean = oldItem == newItem
}