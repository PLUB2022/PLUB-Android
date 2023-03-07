package com.plub.presentation.ui.main.home.search

import com.plub.domain.model.enums.DialogMenuItemType
import com.plub.presentation.ui.Event

sealed class SearchingEvent : Event {
    object GoToBack : SearchingEvent()
    object ClearFocus : SearchingEvent()
    object HideKeyboard : SearchingEvent()
    data class GoToRecruit(val id : Int) : SearchingEvent()
    data class GoToHostRecruit(val id : Int) : SearchingEvent()
    object ScrollToTop : SearchingEvent()
    data class ShowSelectSortTypeBottomSheetDialog(val selectedItem: DialogMenuItemType) : SearchingEvent()
}
