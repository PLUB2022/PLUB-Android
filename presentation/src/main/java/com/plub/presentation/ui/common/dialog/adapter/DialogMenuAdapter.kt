package com.plub.presentation.ui.common.dialog.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.plub.domain.model.enums.DialogMenuItemType
import com.plub.presentation.databinding.IncludeItemDialogMenuButtonBinding
import com.plub.presentation.databinding.IncludeItemDialogMenuSpinnerBinding

class DialogMenuAdapter(
    private val listener: Delegate,
    private val viewType:Int,
) : ListAdapter<DialogMenuItemType, RecyclerView.ViewHolder>(DialogMenuDiffCallback()) {

    companion object {
        const val VIEW_TYPE_BUTTON = 0
        const val VIEW_TYPE_SPINNER = 1
    }

    interface Delegate {
        fun onClickMenu(type: DialogMenuItemType)
        val selectedDialogMenuItem:DialogMenuItemType?
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is DialogMenuButtonViewHolder -> holder.bind(currentList[position])
            is DialogMenuSpinnerViewHolder -> holder.bind(currentList[position])
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_BUTTON -> {
                val binding = IncludeItemDialogMenuButtonBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                DialogMenuButtonViewHolder(binding,listener)
            }
            VIEW_TYPE_SPINNER -> {
                val binding = IncludeItemDialogMenuSpinnerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                DialogMenuSpinnerViewHolder(binding,listener)
            }
            else -> throw IllegalAccessException()
        }
    }

    override fun getItemViewType(position: Int): Int {
        return viewType
    }
}

class DialogMenuDiffCallback : DiffUtil.ItemCallback<DialogMenuItemType>() {
    override fun areItemsTheSame(oldItem: DialogMenuItemType, newItem: DialogMenuItemType): Boolean = oldItem == newItem
    override fun areContentsTheSame(oldItem: DialogMenuItemType, newItem: DialogMenuItemType): Boolean = oldItem == newItem
}