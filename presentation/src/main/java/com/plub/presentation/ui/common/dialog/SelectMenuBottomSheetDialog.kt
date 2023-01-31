package com.plub.presentation.ui.common.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.plub.domain.model.enums.DialogMenuItemType
import com.plub.domain.model.enums.DialogMenuType
import com.plub.presentation.databinding.DialogSelectMenuBinding
import com.plub.presentation.ui.common.dialog.adapter.DialogMenuAdapter
import com.plub.presentation.util.serializable

class SelectMenuBottomSheetDialog : BottomSheetDialogFragment() {

    companion object {
        private const val KEY_MENU_TYPE = "KEY_MENU_TYPE"
        private const val KEY_VIEW_TYPE = "KEY_VIEW_TYPE"
        private const val KEY_TITLE = "KEY_TITLE"
        private const val KEY_SELECTED_MENU_TYPE = "KEY_SELECTED_MENU_TYPE"

        fun newInstance(
            menuType: DialogMenuType,
            title:String = "",
            viewType: Int = DialogMenuAdapter.VIEW_TYPE_BUTTON,
            selectedMenuType:DialogMenuItemType? = null,
            menuClick:(DialogMenuItemType) -> Unit
        ) = SelectMenuBottomSheetDialog().apply {
            this.menuClick = menuClick
            arguments = Bundle().apply {
                putSerializable(KEY_MENU_TYPE, menuType)
                putSerializable(KEY_SELECTED_MENU_TYPE, selectedMenuType)
                putInt(KEY_VIEW_TYPE, viewType)
                putString(KEY_TITLE, title)
            }
        }
    }

    private var menuClick:((DialogMenuItemType) -> Unit)? = null
    private val menuType: DialogMenuType by lazy {
        arguments?.serializable(KEY_MENU_TYPE) ?: DialogMenuType.IMAGE
    }
    private val selectedMenuType: DialogMenuItemType? by lazy {
        arguments?.serializable(KEY_SELECTED_MENU_TYPE)
    }
    private val viewType: Int by lazy {
        arguments?.getInt(KEY_VIEW_TYPE, DialogMenuAdapter.VIEW_TYPE_BUTTON) ?: DialogMenuAdapter.VIEW_TYPE_BUTTON
    }
    private val title: String by lazy {
        arguments?.getString(KEY_TITLE) ?: ""
    }

    private val binding: DialogSelectMenuBinding by lazy {
        DialogSelectMenuBinding.inflate(layoutInflater)
    }

    private val listAdapter: DialogMenuAdapter by lazy {
        DialogMenuAdapter(object : DialogMenuAdapter.Delegate {
            override fun onClickMenu(type: DialogMenuItemType) {
                dismiss()
                menuClick?.invoke(type)
            }

            override val selectedDialogMenuItem: DialogMenuItemType? = selectedMenuType
        }, viewType)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bottomSheetDialog = dialog as BottomSheetDialog
        bottomSheetDialog.behavior.apply {
            isDraggable = false
            state = BottomSheetBehavior.STATE_EXPANDED
        }

        binding.apply {
            recyclerView.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = listAdapter
            }
            textViewTitle.apply {
                visibility = if(title.isEmpty()) View.GONE else View.VISIBLE
                text = title
            }
        }

        listAdapter.submitList(getMenuList())
    }

    private fun getMenuList():List<DialogMenuItemType> {
        return when(menuType) {
            DialogMenuType.IMAGE -> listOf(
                DialogMenuItemType.CAMERA_IMAGE,
                DialogMenuItemType.ALBUM_IMAGE,
                DialogMenuItemType.DEFAULT_IMAGE
            )
            DialogMenuType.SORT_TYPE -> listOf(
                DialogMenuItemType.SORT_TYPE_POPULAR,
                DialogMenuItemType.SORT_TYPE_NEW,
            )
        }
    }
}