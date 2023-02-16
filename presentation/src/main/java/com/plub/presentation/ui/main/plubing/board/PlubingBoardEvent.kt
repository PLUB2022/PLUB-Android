package com.plub.presentation.ui.main.plubing.board

import com.plub.domain.model.enums.DialogMenuType
import com.plub.presentation.ui.Event

sealed class PlubingBoardEvent : Event {
    object NotifyClipBoardChanged : PlubingBoardEvent()
    data class ShowMenuBottomSheetDialog(val id: Int, val menuType: DialogMenuType) : PlubingBoardEvent()
    data class GoToReportBoard(val id: Int) : PlubingBoardEvent()
    data class GoToEditBoard(val id: Int) : PlubingBoardEvent()
}
