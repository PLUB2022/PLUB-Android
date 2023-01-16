package com.plub.presentation.ui.home.adapter.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.plub.domain.model.vo.home.categorylistresponsevo.CategoriesDataResponseVo
import com.plub.presentation.databinding.IncludeItemMainCategoryBinding
import com.plub.presentation.ui.home.adapter.MainCategoryAdapter
import com.plub.presentation.util.GlideUtil

class MainCategoryViewHolder (
    private val binding : IncludeItemMainCategoryBinding,
    private val listener : MainCategoryAdapter.MainCategoryDelegate
) : RecyclerView.ViewHolder(binding.root){


    fun bind(item: CategoriesDataResponseVo) {
        binding.apply {
            GlideUtil.loadImage(root.context, item.icon, imageViewCategory)
            textViewTitleCategory.text = item.name


            constraintLayoutCategoryTouch.setOnClickListener {
                listener.onClick(item.id)
            }
        }
    }
}