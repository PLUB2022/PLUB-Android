package com.plub.presentation.ui.home.plubing.plubhome.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.plub.domain.model.vo.home.categorylistresponsevo.CategoryListDataResponseVo
import com.plub.presentation.databinding.IncludeItemLayoutHomeCategoryBinding
import com.plub.presentation.ui.home.plubing.plubhome.viewholder.HomeCategoryParentViewHoler


class MainCategoryAdapter(private val listener: MainCategoryDelegate) : ListAdapter<CategoryListDataResponseVo, RecyclerView.ViewHolder>(
    MainCategoryDiffCallBack()
){

    interface MainCategoryDelegate {
        fun onClick(categoryId: Int, categoryName : String)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is HomeCategoryParentViewHoler -> holder.bind(currentList[position])
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = IncludeItemLayoutHomeCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HomeCategoryParentViewHoler(binding, listener)
    }

}

class MainCategoryDiffCallBack : DiffUtil.ItemCallback<CategoryListDataResponseVo>() {
    override fun areItemsTheSame(oldItem:CategoryListDataResponseVo, newItem: CategoryListDataResponseVo): Boolean = oldItem.categories == newItem.categories
    override fun areContentsTheSame(oldItem: CategoryListDataResponseVo, newItem: CategoryListDataResponseVo): Boolean = oldItem == newItem
}
