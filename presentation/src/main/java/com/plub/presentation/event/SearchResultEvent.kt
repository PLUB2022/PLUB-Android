package com.plub.presentation.event

import com.plub.domain.model.enums.DialogMenuItemType

sealed class SearchResultEvent : Event {
    object HideKeyboard : SearchResultEvent()
    object ScrollToTop : SearchResultEvent()
    data class ShowSelectSortTypeBottomSheetDialog(val selectedItem: DialogMenuItemType) : SearchResultEvent()
}
