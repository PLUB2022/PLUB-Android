package com.plub.presentation.ui.main.plubing.notice

import com.plub.domain.model.enums.DialogMenuType
import com.plub.domain.model.enums.NoticeType
import com.plub.domain.model.enums.WriteType
import com.plub.domain.model.vo.notice.NoticeVo
import com.plub.presentation.parcelableVo.ParseNoticeVo
import com.plub.presentation.ui.Event

sealed class NoticeEvent : Event {
    object GoToBack : NoticeEvent()
    data class ShowMenuBottomSheetDialog(val menuType: DialogMenuType, val noticeVo: NoticeVo) : NoticeEvent()
    data class GoToDetailNotice(val noticeType: NoticeType, val noticeId: Int) : NoticeEvent()
    data class GoToWriteNotice(val writeType: WriteType, val vo: ParseNoticeVo? = null) : NoticeEvent()
}
