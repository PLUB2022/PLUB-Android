package com.plub.presentation.ui.home.plubing.plubhome.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.plub.domain.model.vo.home.categorylistresponsevo.CategoriesDataResponseVo
import com.plub.presentation.databinding.IncludeItemHomeCategoryBinding
import com.plub.presentation.ui.home.plubing.plubhome.viewholder.HomeCategoryViewHolder


class MainCategoryItemAdapter(private val listener: MainCategoryAdapter.MainCategoryDelegate) : ListAdapter<CategoriesDataResponseVo, RecyclerView.ViewHolder>(
    MainCategoryItemDiffCallBack()
){
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is HomeCategoryViewHolder -> holder.bind(currentList[position])
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = IncludeItemHomeCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HomeCategoryViewHolder(binding, listener)
    }

}



class MainCategoryItemDiffCallBack : DiffUtil.ItemCallback<CategoriesDataResponseVo>() {
    override fun areItemsTheSame(oldItem: CategoriesDataResponseVo, newItem: CategoriesDataResponseVo): Boolean = oldItem.id == newItem.id
    override fun areContentsTheSame(oldItem: CategoriesDataResponseVo, newItem: CategoriesDataResponseVo): Boolean = oldItem == newItem
}
