package com.plub.presentation.ui.main.home.plubhome.viewholder

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.plub.domain.model.vo.home.categoryResponseVo.CategoriesDataResponseVo
import com.plub.presentation.databinding.IncludeItemHomeCategoryBinding
import com.plub.presentation.ui.common.decoration.GridSpaceDecoration
import com.plub.presentation.ui.common.decoration.VerticalSpaceDecoration
import com.plub.presentation.ui.main.home.plubhome.adapter.HomeAdapter
import com.plub.presentation.ui.main.home.plubhome.adapter.HomeCategoryAdapter
import com.plub.presentation.util.px

class HomeCategoryParentViewHolder (
    private val binding : IncludeItemHomeCategoryBinding,
    private val listener : HomeAdapter.HomeDelegate,
) : RecyclerView.ViewHolder(binding.root){

    companion object{
        const val ITEM_SPAN = 4
        const val VERTICAL_SPACE = 10
    }

    private val homeAdapter : HomeCategoryAdapter by lazy {
        HomeCategoryAdapter(listener)
    }

    init {

    }


    fun bind(item: List<CategoriesDataResponseVo>) {
        homeAdapter.submitList(item)
        binding.recyclerViewCategory.apply {
            layoutManager = GridLayoutManager(context, ITEM_SPAN)
            addItemDecoration(GridSpaceDecoration(ITEM_SPAN, 8.px, VERTICAL_SPACE.px, false))
            adapter = homeAdapter
        }
    }
}