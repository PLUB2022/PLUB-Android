package com.plub.presentation.ui.home.plubing.plubhome.viewholder

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.plub.domain.model.vo.home.categorylistresponsevo.CategoryListDataResponseVo
import com.plub.presentation.databinding.IncludeItemLayoutHomeCategoryBinding
import com.plub.presentation.ui.home.plubing.plubhome.adapter.MainCategoryAdapter
import com.plub.presentation.ui.home.plubing.plubhome.adapter.MainCategoryItemAdapter

class HomeCategoryParentViewHoler (
    private val binding : IncludeItemLayoutHomeCategoryBinding,
    private val listener: MainCategoryAdapter.MainCategoryDelegate
) : RecyclerView.ViewHolder(binding.root){

    companion object{
        const val ITEM_SPAN_COUNT = 4
    }
    private val listAdapter = MainCategoryItemAdapter(listener)

    init {
        binding.recyclerViewMainCategory.apply {
            layoutManager = GridLayoutManager(context, ITEM_SPAN_COUNT)
            adapter = listAdapter
        }
    }


    fun bind(item: CategoryListDataResponseVo) {
        binding.apply {
            listAdapter.submitList(item.categories)
        }
    }
}