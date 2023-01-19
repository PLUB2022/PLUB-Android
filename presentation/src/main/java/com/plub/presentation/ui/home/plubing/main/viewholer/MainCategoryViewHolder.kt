package com.plub.presentation.ui.home.plubing.main.viewholer

import androidx.recyclerview.widget.RecyclerView
import com.plub.domain.model.vo.home.categorylistresponsevo.CategoriesDataResponseVo
import com.plub.presentation.databinding.IncludeItemMainCategoryBinding
import com.plub.presentation.ui.home.plubing.main.adapter.MainCategoryAdapter
import com.plub.presentation.util.GlideUtil

class MainCategoryViewHolder (
    private val binding : IncludeItemMainCategoryBinding,
    private val listener : MainCategoryAdapter.MainCategoryDelegate
) : RecyclerView.ViewHolder(binding.root){
    private var categoryId : Int = 0

    init {
        binding.apply {
            constraintLayoutCategoryTouch.setOnClickListener {
                listener.onClick(categoryId, textViewTitleCategory.text.toString())
            }
        }
    }


    fun bind(item: CategoriesDataResponseVo) {
        binding.apply {
            GlideUtil.loadImage(root.context, item.icon, imageViewCategory)
            textViewTitleCategory.text = item.name

            categoryId = item.id
        }
    }
}