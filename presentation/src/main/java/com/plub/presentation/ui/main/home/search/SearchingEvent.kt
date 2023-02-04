package com.plub.presentation.ui.main.home.search

import com.plub.domain.model.enums.DialogMenuItemType
import com.plub.presentation.ui.Event

sealed class SearchingEvent : Event {
    object HideKeyboard : SearchingEvent()
    object ScrollToTop : SearchingEvent()
    data class ShowSelectSortTypeBottomSheetDialog(val selectedItem: DialogMenuItemType) : SearchingEvent()
}
