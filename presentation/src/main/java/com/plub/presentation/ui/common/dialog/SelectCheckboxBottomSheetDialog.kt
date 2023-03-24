package com.plub.presentation.ui.common.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.plub.domain.model.enums.DialogCheckboxItemType
import com.plub.domain.model.enums.DialogCheckboxType
import com.plub.presentation.databinding.DialogSelectCheckboxBinding
import com.plub.presentation.ui.common.dialog.adapter.DialogCheckboxAdapter
import com.plub.presentation.util.serializable

class SelectCheckboxBottomSheetDialog : BottomSheetDialogFragment() {

    companion object {
        private const val KEY_MENU_TYPE = "KEY_MENU_TYPE"
        private const val KEY_TITLE = "KEY_TITLE"
        private const val KEY_ICON = "KEY_ICON"
        private const val KEY_SELECTED_MENU_TYPE = "KEY_SELECTED_MENU_TYPE"

        fun newInstance(
            checkboxType: DialogCheckboxType,
            title: String,
            icon: Int,
            selectedCheckboxType: DialogCheckboxItemType? = null,
            menuClick: (DialogCheckboxItemType) -> Unit
        ) = SelectCheckboxBottomSheetDialog().apply {
            this.menuClick = menuClick
            arguments = Bundle().apply {
                putSerializable(KEY_MENU_TYPE, checkboxType)
                putSerializable(KEY_SELECTED_MENU_TYPE, selectedCheckboxType)
                putString(KEY_TITLE, title)
                putInt(KEY_ICON, icon)
            }
        }
    }

    private var menuClick: ((DialogCheckboxItemType) -> Unit)? = null
    private val checkboxType: DialogCheckboxType by lazy {
        arguments?.serializable(KEY_MENU_TYPE) ?: DialogCheckboxType.ALARM_TYPE
    }
    private val selectedCheckboxType: DialogCheckboxItemType? by lazy {
        arguments?.serializable(KEY_SELECTED_MENU_TYPE)
    }
    private val title: String by lazy {
        arguments?.getString(KEY_TITLE) ?: ""
    }
    private val icon: Int by lazy {
        arguments?.getInt(KEY_ICON) ?: 0
    }

    private val binding: DialogSelectCheckboxBinding by lazy {
        DialogSelectCheckboxBinding.inflate(layoutInflater)
    }

    private val listAdapter: DialogCheckboxAdapter by lazy {
        DialogCheckboxAdapter(object : DialogCheckboxAdapter.Delegate {
            override fun onClickMenu(type: DialogCheckboxItemType) {
                dismiss()
                menuClick?.invoke(type)
            }

            override val selectedDialogMenuItem: DialogCheckboxItemType? = selectedCheckboxType
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
            textViewTitle.apply {
                visibility = if (title.isEmpty()) View.GONE else View.VISIBLE
                text = title
            }
            imageViewIcon.setImageResource(icon)
        }

        listAdapter.submitList(getCheckboxList())
    }

    private fun getCheckboxList(): List<DialogCheckboxItemType> {
        return when (checkboxType) {
            DialogCheckboxType.ALARM_TYPE -> listOf(
                DialogCheckboxItemType.ALARM_NONE,
                DialogCheckboxItemType.ALARM_FIVE_MINUTES,
                DialogCheckboxItemType.ALARM_FIFTEEN_MINUTES,
                DialogCheckboxItemType.ALARM_THIRTY_MINUTES,
                DialogCheckboxItemType.ALARM_ONE_HOUR,
                DialogCheckboxItemType.ALARM_ONE_DAY,
                DialogCheckboxItemType.ALARM_ONE_WEEK
            )
        }
    }
}