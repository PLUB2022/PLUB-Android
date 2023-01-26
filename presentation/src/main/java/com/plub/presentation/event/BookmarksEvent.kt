package com.plub.presentation.event

sealed class BookmarksEvent : Event {
    object ScrollToTop : BookmarksEvent()
}
