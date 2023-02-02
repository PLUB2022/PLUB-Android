package com.plub.presentation.event

import com.plub.domain.model.enums.DialogMenuItemType
import com.plub.presentation.ui.Event

sealed class CategoryChoiceEvent : Event {
    object GoToBack : CategoryChoiceEvent()
    object GoToSearch : CategoryChoiceEvent()
    object GoToCreate : CategoryChoiceEvent()
    data class ShowSelectSortTypeBottomSheetDialog(val selectedItem: DialogMenuItemType) : CategoryChoiceEvent()
}