package com.plub.presentation.ui.main.home.profile

import com.plub.presentation.ui.Event

sealed class MyPageEvent : Event {
    object GoToMyApplication : MyPageEvent()
    object GoToOtherApplication : MyPageEvent()
    data class ReadMore(val isExpandText : Boolean) : MyPageEvent()
}
