package com.plub.domain.model.vo.notice

import com.plub.domain.model.DomainModel
import com.plub.domain.model.enums.NoticeType
import com.plub.domain.model.enums.TodoItemViewType

data class GetNoticeListRequestVo(
    val noticeType: NoticeType,
    val plubbingId: Int = -1,
) : DomainModel