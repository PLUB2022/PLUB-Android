package com.plub.presentation.event

import com.plub.domain.model.enums.PlubSearchType

sealed class SearchingEvent : Event {
    object HideKeyboard : SearchingEvent()
    object Clear : SearchingEvent()
    data class GoToSearchResult(val search: String) : SearchingEvent()
}
