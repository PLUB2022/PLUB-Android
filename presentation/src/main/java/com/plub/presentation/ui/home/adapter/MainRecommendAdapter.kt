package com.plub.presentation.ui.home.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.plub.domain.model.vo.home.GatheringItemVo
import com.plub.presentation.databinding.IncludeItemRecommendGatheringListItemBinding
import com.plub.presentation.ui.home.adapter.viewholder.MainRecommendViewHolder
import com.plub.presentation.ui.home.plubing.categoryChoice.CategoryChoiceViewModel


class MainRecommendAdapter() : ListAdapter<GatheringItemVo, RecyclerView.ViewHolder>(
    MainGatheringDiffCallBack()
){
    private lateinit var viewModel: CategoryChoiceViewModel
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is MainRecommendViewHolder -> holder.bind(currentList[position])
        }
        holder.itemView.setOnClickListener {
            Log.d("어뎁터에서 클릭", currentList[position].title)
            viewModel.goToCategoryChoice()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = IncludeItemRecommendGatheringListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MainRecommendViewHolder(binding)
    }

    fun setViewmodel(vm : CategoryChoiceViewModel){
        viewModel = vm
    }

}

class MainGatheringDiffCallBack : DiffUtil.ItemCallback<GatheringItemVo>() {
    override fun areItemsTheSame(oldItem: GatheringItemVo, newItem: GatheringItemVo): Boolean = oldItem == newItem
    override fun areContentsTheSame(oldItem: GatheringItemVo, newItem: GatheringItemVo): Boolean = oldItem == newItem
}