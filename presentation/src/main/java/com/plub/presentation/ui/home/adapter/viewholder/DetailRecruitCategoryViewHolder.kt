package com.plub.presentation.ui.home.adapter.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.plub.domain.model.vo.home.categorylistresponsevo.CategoriesDataResponseVo
import com.plub.presentation.databinding.IncludeItemMainCategoryBinding
import com.plub.presentation.databinding.IncludeItemPlubbingSubHobbyBinding
import com.plub.presentation.ui.home.adapter.MainCategoryAdapter
import com.plub.presentation.util.GlideUtil

class DetailRecruitCategoryViewHolder(private val binding : IncludeItemPlubbingSubHobbyBinding,
) : RecyclerView.ViewHolder(binding.root){


    fun bind(item: String) {
        binding.apply {
            textViewSubHobbyName.text = item
        }
    }
}