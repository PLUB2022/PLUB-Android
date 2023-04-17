package com.plub.presentation.ui.common.dialog.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.plub.domain.model.enums.DialogCheckboxItemType
import com.plub.domain.model.enums.DialogMenuItemType
import com.plub.presentation.databinding.IncludeItemDialogCheckboxSpinnerBinding
import com.plub.presentation.databinding.IncludeItemDialogMenuButtonBinding
import com.plub.presentation.databinding.IncludeItemDialogMenuSpinnerBinding

class DialogCheckboxAdapter(
    private val listener: Delegate
) : ListAdapter<DialogCheckboxItemType, RecyclerView.ViewHolder>(DialogCheckboxDiffCallback()) {

    interface Delegate {
        fun onClickMenu(type: DialogCheckboxItemType)
        val selectedDialogMenuItem:DialogCheckboxItemType?
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is DialogCheckboxSpinnerViewHolder -> holder.bind(currentList[position])
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = IncludeItemDialogCheckboxSpinnerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DialogCheckboxSpinnerViewHolder(binding,listener)
    }
}

class DialogCheckboxDiffCallback : DiffUtil.ItemCallback<DialogCheckboxItemType>() {
    override fun areItemsTheSame(oldItem: DialogCheckboxItemType, newItem: DialogCheckboxItemType): Boolean = oldItem == newItem
    override fun areContentsTheSame(oldItem: DialogCheckboxItemType, newItem: DialogCheckboxItemType): Boolean = oldItem == newItem
}