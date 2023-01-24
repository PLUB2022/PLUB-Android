package com.plub.presentation.event

sealed class SearchResultEvent : Event {
    object ScrollToTop : SearchResultEvent()
}
