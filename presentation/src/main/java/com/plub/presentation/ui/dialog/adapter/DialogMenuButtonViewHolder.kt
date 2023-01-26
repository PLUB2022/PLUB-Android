package com.plub.presentation.ui.dialog.adapter

import androidx.recyclerview.widget.RecyclerView
import com.plub.domain.model.enums.DialogMenuItemType
import com.plub.presentation.R
import com.plub.presentation.databinding.IncludeItemDialogMenuButtonBinding

class DialogMenuButtonViewHolder(
    private val binding: IncludeItemDialogMenuButtonBinding,
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
            imageViewIcon.setImageResource(getMenuIconRes(item))
            textViewContent.text = root.context.getString(getMenuContentStringRes(item))
        }
    }

    private fun getMenuIconRes(menuType: DialogMenuItemType):Int {
        return when(menuType) {
            DialogMenuItemType.CAMERA_IMAGE -> R.drawable.ic_camera
            DialogMenuItemType.ALBUM_IMAGE -> R.drawable.ic_album
            DialogMenuItemType.DEFAULT_IMAGE -> R.drawable.ic_album
            else -> throw IllegalAccessException()
        }
    }
    private fun getMenuContentStringRes(menuType: DialogMenuItemType):Int {
        return when(menuType) {
            DialogMenuItemType.CAMERA_IMAGE -> R.string.dialog_menu_camera_image
            DialogMenuItemType.ALBUM_IMAGE -> R.string.dialog_menu_album_image
            DialogMenuItemType.DEFAULT_IMAGE -> R.string.dialog_menu_default_image
            else -> throw IllegalAccessException()
        }
    }
}