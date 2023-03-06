package com.plub.presentation.ui.main.home.plubhome.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.plub.domain.model.vo.home.categoryResponseVo.CategoriesDataResponseVo
import com.plub.presentation.databinding.*
import com.plub.presentation.ui.main.home.plubhome.viewholder.*

class HomeCategoryAdapter(private val listener: HomeAdapter.HomeDelegate) : ListAdapter<CategoriesDataResponseVo, RecyclerView.ViewHolder>(
    HomeCategoryDiffCallBack()
){
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is HomeCategoryViewHolder -> holder.bind(currentList[position])
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = IncludeItemCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HomeCategoryViewHolder(binding, listener)
    }
}

class HomeCategoryDiffCallBack : DiffUtil.ItemCallback<CategoriesDataResponseVo>() {
    override fun areItemsTheSame(oldItem: CategoriesDataResponseVo, newItem: CategoriesDataResponseVo): Boolean = oldItem.id == newItem.id
    override fun areContentsTheSame(oldItem: CategoriesDataResponseVo, newItem: CategoriesDataResponseVo): Boolean = oldItem == newItem
}