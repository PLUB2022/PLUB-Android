package com.plub.presentation.ui.home.adapter.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.plub.presentation.databinding.IncludeItemPlubbingSubHobbyBinding

class DetailRecruitCategoryViewHolder(private val binding : IncludeItemPlubbingSubHobbyBinding,
) : RecyclerView.ViewHolder(binding.root){


    fun bind(item: String) {
        binding.apply {
            textViewSubHobbyName.text = item
        }
    }
}