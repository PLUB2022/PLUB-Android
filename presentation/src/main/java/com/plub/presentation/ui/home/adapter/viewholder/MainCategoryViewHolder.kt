package com.plub.presentation.ui.home.adapter.viewholder

import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import com.plub.domain.model.vo.home.SampleCategoryVo
import com.plub.presentation.databinding.ItemMainCategoryBinding

class MainCategoryViewHolder (
    private val binding : ItemMainCategoryBinding,
) : RecyclerView.ViewHolder(binding.root){

    fun bind(item: SampleCategoryVo) {
        binding.apply {
            //iconCategory = item.img_res
            tvTitleCategory.text = item.title

        }
    }
}