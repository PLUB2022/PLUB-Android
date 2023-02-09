package com.plub.presentation.ui.main.home.plubhome.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.plub.domain.model.enums.HomeCategoryViewType
import com.plub.domain.model.vo.home.categorylistresponsevo.CategoryListDataResponseVo
import com.plub.presentation.databinding.IncludeItemHomeTitleBinding
import com.plub.presentation.databinding.IncludeItemLayoutHomeCategoryBinding
import com.plub.presentation.ui.main.home.plubhome.viewholder.HomeCategoryFirstViewHolder
import com.plub.presentation.ui.main.home.plubhome.viewholder.HomeCategoryParentViewHolder


class HomeCategoryParentAdapter(private val listener: HomeCategoryDelegate) : ListAdapter<CategoryListDataResponseVo, RecyclerView.ViewHolder>(
    HomeCategoryDiffCallBack()
){

    interface HomeCategoryDelegate {
        fun onClick(categoryId: Int, categoryName : String)
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is HomeCategoryParentViewHolder -> holder.bind(currentList[position])
            is HomeCategoryFirstViewHolder -> holder.bind()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when(HomeCategoryViewType.valueOf(viewType)){
            HomeCategoryViewType.FIRST_VIEW -> {
                val binding = IncludeItemHomeTitleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                return HomeCategoryFirstViewHolder(binding)
            }
            HomeCategoryViewType.CATEGORY_VIEW -> {
                val binding = IncludeItemLayoutHomeCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                return HomeCategoryParentViewHolder(binding, listener)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return currentList[position].viewType.type
    }
}

class HomeCategoryDiffCallBack : DiffUtil.ItemCallback<CategoryListDataResponseVo>() {
    override fun areItemsTheSame(oldItem:CategoryListDataResponseVo, newItem: CategoryListDataResponseVo): Boolean = oldItem.categories == newItem.categories
    override fun areContentsTheSame(oldItem: CategoryListDataResponseVo, newItem: CategoryListDataResponseVo): Boolean = oldItem == newItem
}
