package com.plub.presentation.ui.main.profile

import com.plub.domain.model.enums.MyPageGatheringMyType
import com.plub.presentation.ui.Event

sealed class MyPageEvent : Event {
    data class GoToMyApplication(val plubbingId : Int) : MyPageEvent()
    data class GoToOtherApplication(val plubbingId : Int) : MyPageEvent()
    data class GoToActiveGathering(val plubbingId: Int, val gatheringType : MyPageGatheringMyType) : MyPageEvent()
    object GoToSetting : MyPageEvent()
    object GoToHome : MyPageEvent()
    data class ReadMore(val isExpandText : Boolean) : MyPageEvent()
}
