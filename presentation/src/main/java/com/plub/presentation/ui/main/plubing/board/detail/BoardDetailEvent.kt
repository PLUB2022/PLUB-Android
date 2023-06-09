package com.plub.presentation.ui.main.plubing.board.detail

import com.plub.domain.model.enums.DialogMenuType
import com.plub.domain.model.vo.board.BoardCommentVo
import com.plub.presentation.ui.Event

sealed class BoardDetailEvent : Event {
    object Finish : BoardDetailEvent()
    object NotifyBoardDetailInfoNotify : BoardDetailEvent()
    object HideKeyboard : BoardDetailEvent()
    object ShowKeyboard : BoardDetailEvent()
    object GoToBack : BoardDetailEvent()
    data class ShowMenuBottomSheetDialog(val menuType: DialogMenuType, val commentVo: BoardCommentVo = BoardCommentVo()) : BoardDetailEvent()
    data class ScrollToPosition(val position: Int) : BoardDetailEvent()
    data class GoToReportBoard(val plubingId: Int, val feedId: Int) : BoardDetailEvent()
    data class GoToEditBoard(val plubingId: Int, val feedId: Int) : BoardDetailEvent()
    data class GoToReportComment(val plubingId: Int, val feedId: Int, val commentId:Int) : BoardDetailEvent()
}
