package com.plub.presentation.ui.main.home.search

import com.plub.presentation.ui.Event

sealed class SearchingEvent : Event {
    object HideKeyboard : SearchingEvent()
    object Clear : SearchingEvent()
    data class GoToSearchResult(val search: String) : SearchingEvent()
}
