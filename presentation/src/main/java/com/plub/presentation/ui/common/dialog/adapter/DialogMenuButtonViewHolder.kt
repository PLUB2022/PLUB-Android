package com.plub.presentation.ui.common.dialog.adapter

import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.plub.domain.model.enums.DialogMenuItemType
import com.plub.presentation.R
import com.plub.presentation.databinding.IncludeItemDialogMenuButtonBinding
import com.plub.presentation.util.onThrottleClick

class DialogMenuButtonViewHolder(
    private val binding: IncludeItemDialogMenuButtonBinding,
    private val listener: DialogMenuAdapter.Delegate
) : RecyclerView.ViewHolder(binding.root) {

    init {
        binding.root.onThrottleClick {
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
            textViewContent.setTextColor(ContextCompat.getColor(root.context, getMenuContentColorRes(item)))
        }
    }

    private fun getMenuIconRes(menuType: DialogMenuItemType):Int {
        return when(menuType) {
            DialogMenuItemType.TODO_PROOF,
            DialogMenuItemType.CAMERA_IMAGE -> R.drawable.ic_camera
            DialogMenuItemType.ALBUM_IMAGE -> R.drawable.ic_album
            DialogMenuItemType.DEFAULT_IMAGE -> R.drawable.ic_album
            DialogMenuItemType.NOTICE_DELETE,
            DialogMenuItemType.TODO_DELETE,
            DialogMenuItemType.BOARD_DELETE,
            DialogMenuItemType.BOARD_COMMENT_DELETE -> R.drawable.ic_delete_red
            DialogMenuItemType.TODO_PLANNER,
            DialogMenuItemType.NOTICE_EDIT,
            DialogMenuItemType.TODO_EDIT,
            DialogMenuItemType.BOARD_EDIT,
            DialogMenuItemType.BOARD_COMMENT_EDIT -> R.drawable.ic_edit_black
            DialogMenuItemType.TODO_REPORT,
            DialogMenuItemType.BOARD_REPORT,
            DialogMenuItemType.BOARD_COMMENT_REPORT -> R.drawable.ic_report_blue
            DialogMenuItemType.BOARD_FIX_CLIP,
            DialogMenuItemType.BOARD_RELEASE_CLIP,
            DialogMenuItemType.BOARD_FIX_OR_RELEASE_CLIP -> R.drawable.ic_clipboard_black
            else -> throw IllegalAccessException()
        }
    }

    private fun getMenuContentStringRes(menuType: DialogMenuItemType):Int {
        return when(menuType) {
            DialogMenuItemType.CAMERA_IMAGE -> R.string.dialog_menu_camera_image
            DialogMenuItemType.ALBUM_IMAGE -> R.string.dialog_menu_album_image
            DialogMenuItemType.DEFAULT_IMAGE -> R.string.dialog_menu_default_image
            DialogMenuItemType.BOARD_DELETE -> R.string.dialog_menu_board_delete
            DialogMenuItemType.BOARD_COMMENT_DELETE  -> R.string.dialog_menu_comment_delete
            DialogMenuItemType.BOARD_EDIT -> R.string.dialog_menu_board_edit
            DialogMenuItemType.BOARD_COMMENT_EDIT  -> R.string.dialog_menu_comment_edit
            DialogMenuItemType.BOARD_REPORT -> R.string.dialog_menu_board_report
            DialogMenuItemType.BOARD_COMMENT_REPORT  -> R.string.dialog_menu_comment_report
            DialogMenuItemType.BOARD_FIX_CLIP -> R.string.dialog_menu_board_fix_clip
            DialogMenuItemType.BOARD_RELEASE_CLIP  -> R.string.dialog_menu_board_release_clip
            DialogMenuItemType.BOARD_FIX_OR_RELEASE_CLIP  -> R.string.dialog_menu_board_fix_or_release_clip
            DialogMenuItemType.TODO_PROOF  -> R.string.dialog_menu_todo_proof
            DialogMenuItemType.TODO_PLANNER  -> R.string.dialog_menu_todo_planner
            DialogMenuItemType.TODO_REPORT  -> R.string.dialog_menu_todo_report
            DialogMenuItemType.TODO_DELETE  -> R.string.dialog_menu_todo_delete
            DialogMenuItemType.TODO_EDIT  -> R.string.dialog_menu_todo_edit
            DialogMenuItemType.NOTICE_DELETE  -> R.string.dialog_menu_notice_delete
            DialogMenuItemType.NOTICE_EDIT  -> R.string.dialog_menu_notice_edit
            else -> throw IllegalAccessException()
        }
    }

    private fun getMenuContentColorRes(menuType: DialogMenuItemType):Int {
        return when(menuType) {
            DialogMenuItemType.NOTICE_DELETE,
            DialogMenuItemType.TODO_DELETE,
            DialogMenuItemType.BOARD_DELETE,
            DialogMenuItemType.BOARD_COMMENT_DELETE -> R.color.color_f75b2b
            else -> R.color.color_363636
        }
    }
}