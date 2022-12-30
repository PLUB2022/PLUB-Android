package com.plub.presentation.ui.dialog

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
import com.plub.presentation.ui.dialog.adapter.DialogMenuAdapter

class SelectMenuBottomSheetDialog : BottomSheetDialogFragment() {

    companion object {
        private const val KEY_MENU_TYPE = "KEY_MENU_TYPE"

        fun newInstance(
            menuType: DialogMenuType,
            menuClick:(DialogMenuItemType) -> Unit
        ) = SelectMenuBottomSheetDialog().apply {
            this.menuClick = menuClick
            arguments = Bundle().apply {
                putSerializable(KEY_MENU_TYPE, menuType)
            }
        }
    }

    private var menuClick:((DialogMenuItemType) -> Unit)? = null
    private val menuType: DialogMenuType? by lazy {
        arguments?.getSerializable(KEY_MENU_TYPE) as? DialogMenuType
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
        })
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
        }

        listAdapter.submitList(getMenuList())
    }

    private fun getMenuList():List<DialogMenuItemType> {
        return when(menuType ?: DialogMenuType.IMAGE) {
            DialogMenuType.IMAGE -> listOf(
                DialogMenuItemType.CAMERA_IMAGE,
                DialogMenuItemType.ALBUM_IMAGE,
                DialogMenuItemType.DEFAULT_IMAGE
            )
        }
    }
}