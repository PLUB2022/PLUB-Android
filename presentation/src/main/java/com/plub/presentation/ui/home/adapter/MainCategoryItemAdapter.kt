package com.plub.presentation.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.plub.domain.model.vo.home.categorylistresponsevo.CategoriesDataResponseVo
import com.plub.presentation.databinding.IncludeItemMainCategoryBinding
import com.plub.presentation.ui.home.adapter.viewholder.MainCategoryViewHolder


class MainCategoryItemAdapter(private val listener: MainCategoryAdapter.MainCategoryDelegate) : ListAdapter<CategoriesDataResponseVo, RecyclerView.ViewHolder>(
    MainCategoryItemDiffCallBack()
){
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is MainCategoryViewHolder -> holder.bind(currentList[position])
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = IncludeItemMainCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MainCategoryViewHolder(binding, listener)
    }

}



class MainCategoryItemDiffCallBack : DiffUtil.ItemCallback<CategoriesDataResponseVo>() {
    override fun areItemsTheSame(oldItem: CategoriesDataResponseVo, newItem: CategoriesDataResponseVo): Boolean = oldItem == newItem
    override fun areContentsTheSame(oldItem: CategoriesDataResponseVo, newItem: CategoriesDataResponseVo): Boolean = oldItem == newItem
}

