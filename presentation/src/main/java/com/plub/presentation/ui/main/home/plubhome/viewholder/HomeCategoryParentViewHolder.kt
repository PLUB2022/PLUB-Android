package com.plub.presentation.ui.main.home.plubhome.viewholder

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.plub.domain.model.vo.home.categorylistresponsevo.CategoryListDataResponseVo
import com.plub.presentation.databinding.IncludeItemLayoutHomeCategoryBinding
import com.plub.presentation.ui.common.decoration.GridSpaceDecoration
import com.plub.presentation.ui.main.home.plubhome.adapter.HomeCategoryAdapter
import com.plub.presentation.ui.main.home.plubhome.adapter.HomeCategoryParentAdapter
import com.plub.presentation.util.px

class HomeCategoryParentViewHolder (
    private val binding : IncludeItemLayoutHomeCategoryBinding,
    private val listener : HomeCategoryParentAdapter.HomeCategoryDelegate
) : RecyclerView.ViewHolder(binding.root){

    companion object{
        const val ITEM_SPAN_COUNT = 4
    }

    private val listAdapter = HomeCategoryAdapter(listener)

    init {
        binding.recyclerViewMainCategory.apply {
            layoutManager = GridLayoutManager(context, ITEM_SPAN_COUNT)
            addItemDecoration(GridSpaceDecoration(ITEM_SPAN_COUNT, 5.px, 5.px, false))
            adapter = listAdapter
        }
    }


    fun bind(item: CategoryListDataResponseVo) {
        binding.apply {
            listAdapter.submitList(item.categories)
        }
    }
}