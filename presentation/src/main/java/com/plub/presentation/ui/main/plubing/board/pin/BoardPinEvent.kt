package com.plub.presentation.ui.main.plubing.board.pin

import com.plub.domain.model.enums.DialogMenuType
import com.plub.presentation.ui.Event

sealed class BoardPinEvent : Event {
    data class ShowMenuBottomSheetDialog(val feedId: Int, val menuType: DialogMenuType) : BoardPinEvent()
    data class GoToReportBoard(val feedId: Int) : BoardPinEvent()
    data class GoToEditBoard(val feedId: Int) : BoardPinEvent()
    data class GoToDetailBoard(val feedId:Int) : BoardPinEvent()
    object GoToBack : BoardPinEvent()
    data class ScrollToPosition(val position:Int) : BoardPinEvent()
}
