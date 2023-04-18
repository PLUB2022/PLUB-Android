package com.plub.presentation.ui.main.profile.active.myPost

import com.plub.domain.model.enums.DialogMenuType
import com.plub.presentation.ui.Event

sealed class MyPageAllMyPostEvent : Event {
    data class ShowMenuBottomSheetDialog(val feedId: Int, val menuType: DialogMenuType) : MyPageAllMyPostEvent()
    data class GoToReportBoard(val feedId: Int) : MyPageAllMyPostEvent()
    data class GoToEditBoard(val feedId: Int) : MyPageAllMyPostEvent()
    data class GoToDetailBoard(val feedId:Int) : MyPageAllMyPostEvent()
    object GoToPinBoard : MyPageAllMyPostEvent()
    object GoToBack : MyPageAllMyPostEvent()
}