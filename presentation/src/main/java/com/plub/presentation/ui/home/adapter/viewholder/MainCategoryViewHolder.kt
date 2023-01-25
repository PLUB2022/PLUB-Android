package com.plub.presentation.ui.home.adapter.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.plub.domain.model.vo.home.categorylistresponsevo.CategoriesDataResponseVo
import com.plub.presentation.databinding.IncludeItemMainCategoryBinding
import com.plub.presentation.util.GlideUtil

class MainCategoryViewHolder (
    private val binding : IncludeItemMainCategoryBinding,
) : RecyclerView.ViewHolder(binding.root){

    fun bind(item: CategoriesDataResponseVo) {
        binding.apply {
            GlideUtil.loadImage(root.context, item.icon, iconCategory)
            tvTitleCategory.text = item.name

        }
    }
}