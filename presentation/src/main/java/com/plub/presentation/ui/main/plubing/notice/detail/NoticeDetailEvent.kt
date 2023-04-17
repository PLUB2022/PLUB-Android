package com.plub.presentation.ui.main.plubing.notice.detail

import com.plub.domain.model.enums.DialogMenuType
import com.plub.domain.model.vo.board.BoardCommentVo
import com.plub.presentation.parcelableVo.ParseNoticeVo
import com.plub.presentation.ui.Event
import com.plub.presentation.ui.main.plubing.board.detail.BoardDetailEvent

sealed class NoticeDetailEvent : Event {
    object Finish : NoticeDetailEvent()
    object NotifyBoardDetailInfoNotify : NoticeDetailEvent()
    object HideKeyboard : NoticeDetailEvent()
    object ShowKeyboard : NoticeDetailEvent()
    data class ShowMenuBottomSheetDialog(val menuType: DialogMenuType, val commentVo: BoardCommentVo = BoardCommentVo()) : NoticeDetailEvent()
    data class ScrollToPosition(val position: Int) : NoticeDetailEvent()
    data class GoToReportNotice(val plubingId: Int, val noticeId: Int) : NoticeDetailEvent()
    data class GoToEditNotice(val plubingId: Int, val noticeId: Int) : NoticeDetailEvent()
    data class GoToReportComment(val plubingId: Int, val noticeId: Int, val commentId:Int) : NoticeDetailEvent()
}
