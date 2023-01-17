package com.plub.presentation.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.plub.presentation.databinding.IncludeItemLayoutMainRecommendGatheringNoChocieBinding
import com.plub.presentation.ui.home.adapter.viewholder.MainRecommendXViewHolder


class MainRecommendGatheringXAdapter(private val listener: MainRecommendGatheringXDelegate) : ListAdapter<Int, RecyclerView.ViewHolder>(
    MainRecommendGatheringXDiffCallBack()
){

    interface MainRecommendGatheringXDelegate {
        fun onClick()
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is MainRecommendXViewHolder -> holder.bind(currentList[position])
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = IncludeItemLayoutMainRecommendGatheringNoChocieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MainRecommendXViewHolder(binding, listener)
    }

}

class MainRecommendGatheringXDiffCallBack : DiffUtil.ItemCallback<Int>() {
    override fun areItemsTheSame(oldItem: Int, newItem: Int): Boolean = oldItem == newItem
    override fun areContentsTheSame(oldItem: Int, newItem: Int): Boolean = oldItem == newItem
}