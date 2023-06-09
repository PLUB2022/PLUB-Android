package com.plub.presentation.ui.main.home.bookmark

import com.plub.presentation.ui.Event

sealed class BookmarksEvent : Event {
    object GoToBack : BookmarksEvent()
    object ScrollToTop : BookmarksEvent()
    data class GoToRecruit(val id : Int) : BookmarksEvent()
}
