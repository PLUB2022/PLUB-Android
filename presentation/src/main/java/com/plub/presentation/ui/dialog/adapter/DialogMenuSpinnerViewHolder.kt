package com.plub.presentation.ui.dialog.adapter

import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.plub.domain.model.enums.DialogMenuItemType
import com.plub.presentation.R
import com.plub.presentation.databinding.IncludeItemDialogMenuSpinnerBinding

class DialogMenuSpinnerViewHolder(
    private val binding: IncludeItemDialogMenuSpinnerBinding,
    private val listener:DialogMenuAdapter.Delegate
) : RecyclerView.ViewHolder(binding.root) {

    init {
        binding.root.setOnClickListener {
            menuType?.let {
                listener.onClickMenu(it)
            }
        }
    }

    private var menuType:DialogMenuItemType? = null

    fun bind(item: DialogMenuItemType) {
        menuType = item
        binding.apply {
            val isSelected = item == listener.selectedDialogMenuItem

            val textRes = if(isSelected) R.color.color_5f5ff9 else R.color.color_363636
            val textColor = ContextCompat.getColor(root.context, textRes)
            textViewContent.text = root.context.getString(getMenuContentStringRes(item))
            textViewContent.setTextColor(textColor)
            imageViewCheck.visibility = if(isSelected) View.VISIBLE else View.GONE
        }
    }

    private fun getMenuContentStringRes(menuType: DialogMenuItemType):Int {
        return when(menuType) {
            DialogMenuItemType.SORT_TYPE_POPULAR -> R.string.word_sort_type_popular
            DialogMenuItemType.SORT_TYPE_NEW -> R.string.word_sort_type_new
            else -> throw IllegalAccessException()
        }
    }
}