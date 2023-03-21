package com.plub.presentation.ui.common.dialog.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.plub.domain.model.enums.DialogCheckboxItemType
import com.plub.presentation.R
import com.plub.presentation.databinding.IncludeItemDialogCheckboxSpinnerBinding
import com.plub.presentation.util.onThrottleClick

class DialogCheckboxSpinnerViewHolder(
    private val binding: IncludeItemDialogCheckboxSpinnerBinding,
    private val listener: DialogCheckboxAdapter.Delegate
) : RecyclerView.ViewHolder(binding.root) {

    init {
        binding.root.onThrottleClick {
            menuType?.let {
                listener.onClickMenu(it)
            }
        }
    }

    private var menuType: DialogCheckboxItemType? = null

    fun bind(item: DialogCheckboxItemType) {
        menuType = item
        binding.apply {
            val imageRes = if(item == listener.selectedDialogMenuItem) R.drawable.ic_radio_button_checked else R.drawable.ic_radio_button_unchecked
            imageViewCheck.setImageResource(imageRes)
            textViewContent.text = item.kor
            viewDivider.visibility = if(item == DialogCheckboxItemType.ALARM_ONE_WEEK) View.INVISIBLE else View.VISIBLE
        }
    }
}