package com.plub.presentation.ui.main.gathering.my.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.plub.domain.model.vo.myGathering.MyGatheringResponseVo
import com.plub.presentation.databinding.LayoutRecyclerMyGatheringBinding

class MyGatheringsAdapter : ListAdapter<MyGatheringResponseVo, MyGatheringsAdapter.MyGatheringsViewHolder>(DiffCallback) {

    companion object {
        val DiffCallback = object : DiffUtil.ItemCallback<MyGatheringResponseVo>() {
            override fun areItemsTheSame(oldItem: MyGatheringResponseVo, newItem: MyGatheringResponseVo): Boolean {
                return oldItem.plubbingId == newItem.plubbingId
            }

            override fun areContentsTheSame(oldItem: MyGatheringResponseVo, newItem: MyGatheringResponseVo): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyGatheringsViewHolder {
        val binding = LayoutRecyclerMyGatheringBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyGatheringsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyGatheringsViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class MyGatheringsViewHolder(private val binding: LayoutRecyclerMyGatheringBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: MyGatheringResponseVo) {

            binding.executePendingBindings()
        }
    }
}