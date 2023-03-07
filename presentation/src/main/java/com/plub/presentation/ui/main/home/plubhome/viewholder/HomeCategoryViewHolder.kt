package com.plub.presentation.ui.main.home.plubhome.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.plub.domain.model.vo.home.categoryResponseVo.CategoriesDataResponseVo
import com.plub.presentation.databinding.IncludeItemCategoryBinding
import com.plub.presentation.ui.main.home.plubhome.adapter.HomeAdapter
import com.plub.presentation.util.GlideUtil

class HomeCategoryViewHolder (
    private val binding : IncludeItemCategoryBinding,
    private val listener : HomeAdapter.HomeDelegate,
) : RecyclerView.ViewHolder(binding.root){

    private var itemVo : CategoriesDataResponseVo? = null

    init {
        binding.root.setOnClickListener {
            itemVo?.let { listener.onCategoryClick(it.id, it.name) }
        }
    }


    fun bind(item: CategoriesDataResponseVo) {
        itemVo = item
        binding.apply {
            GlideUtil.loadImage(root.context, item.icon, imageViewCategory)
            textViewCategory.text = item.name
        }
    }
}