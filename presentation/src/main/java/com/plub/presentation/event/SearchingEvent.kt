package com.plub.presentation.event

sealed class SearchingEvent : Event {
    data class GoToSearchResult(val search: String) : SearchingEvent()
}
