package com.plub.presentation.event

import com.plub.domain.model.enums.DialogMenuItemType

sealed class CategoryChoiceEvent : Event {
    object GoToBack : CategoryChoiceEvent()
    object GoToSearch : CategoryChoiceEvent()
    object GoToCreate : CategoryChoiceEvent()
    data class ShowSelectSortTypeBottomSheetDialog(val selectedItem: DialogMenuItemType) : CategoryChoiceEvent()
}