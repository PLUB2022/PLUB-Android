package com.plub.presentation.ui.main.home.profile

import com.plub.presentation.ui.Event

sealed class MyPageEvent : Event {
    data class GoToMyApplication(val plubbingId : Int) : MyPageEvent()
    data class GoToOtherApplication(val plubbingId : Int) : MyPageEvent()
    object GoToSetting : MyPageEvent()
    data class ReadMore(val isExpandText : Boolean) : MyPageEvent()
}
