package com.plub.presentation.ui.dialog.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.plub.domain.model.enums.DialogMenuItemType
import com.plub.presentation.databinding.IncludeItemDialogMenuBinding

class DialogMenuAdapter(
    private val listener: Delegate,
) : ListAdapter<DialogMenuItemType, RecyclerView.ViewHolder>(DialogMenuDiffCallback()) {

    interface Delegate {
        fun onClickMenu(type: DialogMenuItemType)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is DialogMenuItemViewHolder -> holder.bind(currentList[position])
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = IncludeItemDialogMenuBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DialogMenuItemViewHolder(binding,listener)
    }

}

class DialogMenuDiffCallback : DiffUtil.ItemCallback<DialogMenuItemType>() {
    override fun areItemsTheSame(oldItem: DialogMenuItemType, newItem: DialogMenuItemType): Boolean = oldItem == newItem
    override fun areContentsTheSame(oldItem: DialogMenuItemType, newItem: DialogMenuItemType): Boolean = oldItem == newItem
}