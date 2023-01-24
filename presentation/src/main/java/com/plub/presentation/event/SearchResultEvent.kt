package com.plub.presentation.event

sealed class SearchResultEvent : Event {
    object HideKeyboard : SearchResultEvent()
    object ScrollToTop : SearchResultEvent()
}
