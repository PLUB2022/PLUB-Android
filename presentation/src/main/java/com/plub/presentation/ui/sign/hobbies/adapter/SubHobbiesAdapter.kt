package com.plub.presentation.ui.sign.hobbies.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.plub.domain.model.vo.common.SelectedHobbyVo
import com.plub.domain.model.vo.common.SubHobbyVo
import com.plub.presentation.databinding.IncludeItemSubHobbyBinding

class SubHobbiesAdapter(
    private val listener: HobbiesAdapter.Delegate
) : ListAdapter<SubHobbyVo, RecyclerView.ViewHolder>(SubHobbyDiffCallback()) {

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is SubHobbyViewHolder -> holder.bind(currentList[position])
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = IncludeItemSubHobbyBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SubHobbyViewHolder(binding, listener)
    }

    fun updateOnClick(id:Int) {
        val position = currentList.indexOfFirst {
            it.id == id
        }
        notifyItemChanged(position)
    }
}

class SubHobbyDiffCallback : DiffUtil.ItemCallback<SubHobbyVo>() {
    override fun areItemsTheSame(oldItem: SubHobbyVo, newItem: SubHobbyVo): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: SubHobbyVo, newItem: SubHobbyVo): Boolean =
        oldItem.name == newItem.name
}