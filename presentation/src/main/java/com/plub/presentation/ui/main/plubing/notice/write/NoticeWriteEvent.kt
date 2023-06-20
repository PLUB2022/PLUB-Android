package com.plub.presentation.ui.main.plubing.notice.write

import com.plub.presentation.parcelableVo.ParseNoticeVo
import com.plub.presentation.ui.Event

sealed class NoticeWriteEvent : Event {
    data class CompleteEdit(val notice: ParseNoticeVo) : NoticeWriteEvent()
    object CompleteCreate : NoticeWriteEvent()
    object GoToBack : NoticeWriteEvent()
}
