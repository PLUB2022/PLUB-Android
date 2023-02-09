package com.plub.presentation.ui.main.home.categoryChoice

import com.plub.domain.model.enums.DialogMenuItemType
import com.plub.presentation.ui.Event

sealed class CategoryChoiceEvent : Event {
    object GoToBack : CategoryChoiceEvent()
    object GoToSearch : CategoryChoiceEvent()
    object GoToCreate : CategoryChoiceEvent()
    object ScrollTop : CategoryChoiceEvent()
    data class ShowSelectSortTypeBottomSheetDialog(val selectedItem: DialogMenuItemType) : CategoryChoiceEvent()
}