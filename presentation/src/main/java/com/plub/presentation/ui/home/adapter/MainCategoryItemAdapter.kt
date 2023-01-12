package com.plub.presentation.ui.home.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.plub.domain.model.vo.home.SampleCategoryVo
import com.plub.presentation.databinding.ItemMainCategoryBinding
import com.plub.presentation.ui.home.adapter.viewholder.MainCategoryViewHolder
import com.plub.presentation.ui.home.plubing.main.MainFragmentViewModel


class MainCategoryItemAdapter(private val viewModel: MainFragmentViewModel) : ListAdapter<SampleCategoryVo, RecyclerView.ViewHolder>(
    MainCategoryDiffCallBack()
){
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is MainCategoryViewHolder -> holder.bind(currentList[position])
        }
        holder.itemView.setOnClickListener {
            Log.d("어뎁터에서 클릭", currentList[position].title)
            viewModel.goToCategoryChoice()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = ItemMainCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MainCategoryViewHolder(binding)
    }

}



class MainCategoryDiffCallBack : DiffUtil.ItemCallback<SampleCategoryVo>() {
    override fun areItemsTheSame(oldItem: SampleCategoryVo, newItem: SampleCategoryVo): Boolean = oldItem == newItem
    override fun areContentsTheSame(oldItem: SampleCategoryVo, newItem: SampleCategoryVo): Boolean = oldItem == newItem
}

