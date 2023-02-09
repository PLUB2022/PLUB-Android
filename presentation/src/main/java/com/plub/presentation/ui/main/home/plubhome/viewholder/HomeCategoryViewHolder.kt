package com.plub.presentation.ui.main.home.plubhome.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.plub.domain.model.vo.home.categorylistresponsevo.CategoriesDataResponseVo
import com.plub.presentation.databinding.IncludeItemHomeCategoryBinding
import com.plub.presentation.ui.main.home.plubhome.adapter.HomeCategoryParentAdapter
import com.plub.presentation.util.GlideUtil

class HomeCategoryViewHolder (
    private val binding : IncludeItemHomeCategoryBinding,
    private val listener : HomeCategoryParentAdapter.HomeCategoryDelegate,
) : RecyclerView.ViewHolder(binding.root){
    private var categoryId : Int = 0

    init {
        binding.apply {
            constraintLayoutCategoryTouch.setOnClickListener {
                listener.onCategoryClick(categoryId, textViewTitleCategory.text.toString())
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