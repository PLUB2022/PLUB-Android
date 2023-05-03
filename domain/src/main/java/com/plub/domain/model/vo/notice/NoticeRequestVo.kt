package com.plub.domain.model.vo.notice

import com.plub.domain.model.DomainModel
import com.plub.domain.model.enums.NoticeType

data class NoticeRequestVo(
    val noticeType: NoticeType,
    val plubbingId: Int,
    val noticeId: Int = -1,
    val commentId: Int = -1,
) : DomainModel