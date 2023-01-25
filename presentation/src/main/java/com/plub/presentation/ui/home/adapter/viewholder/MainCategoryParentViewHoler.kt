package com.plub.presentation.ui.home.adapter.viewholder

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.plub.domain.model.vo.common.HobbyVo
import com.plub.domain.model.vo.home.categorylistresponsevo.CategoryListDataResponseVo
import com.plub.presentation.databinding.IncludeItemLayoutMainCategoryBinding
import com.plub.presentation.ui.home.adapter.MainCategoryAdapter
import com.plub.presentation.ui.home.adapter.MainCategoryItemAdapter

class MainCategoryParentViewHoler (
    private val binding : IncludeItemLayoutMainCategoryBinding,
    private val listener: MainCategoryAdapter.Delegate
) : RecyclerView.ViewHolder(binding.root){

    private var vo: HobbyVo? = null
    private val listAdapter = MainCategoryItemAdapter(listener)

    init {

        binding.rvMainCategory.apply {
            layoutManager = GridLayoutManager(context, 4)
            adapter = listAdapter
        }
    }


    fun bind(item: CategoryListDataResponseVo) {
        binding.apply {
            listAdapter.submitList(item.categories)

        }
    }
}