package com.plub.presentation.ui.main.home.searchResult

import com.plub.domain.model.enums.DialogMenuItemType
import com.plub.presentation.ui.Event

sealed class SearchResultEvent : Event {
    object HideKeyboard : SearchResultEvent()
    object ScrollToTop : SearchResultEvent()
    object NavigationPopEvent : SearchResultEvent()
    data class ShowSelectSortTypeBottomSheetDialog(val selectedItem: DialogMenuItemType) : SearchResultEvent()
}
