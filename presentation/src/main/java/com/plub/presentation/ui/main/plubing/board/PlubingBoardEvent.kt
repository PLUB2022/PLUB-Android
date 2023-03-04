package com.plub.presentation.ui.main.plubing.board

import com.plub.domain.model.enums.DialogMenuType
import com.plub.presentation.ui.Event

sealed class PlubingBoardEvent : Event {
    object NotifyClipBoardChanged : PlubingBoardEvent()
    data class ShowMenuBottomSheetDialog(val feedId: Int, val menuType: DialogMenuType) : PlubingBoardEvent()
    data class GoToReportBoard(val feedId: Int) : PlubingBoardEvent()
    data class GoToEditBoard(val feedId: Int) : PlubingBoardEvent()
    data class GoToDetailBoard(val feedId:Int) : PlubingBoardEvent()
    data class ScrollToPosition(val position:Int) : PlubingBoardEvent()
}
