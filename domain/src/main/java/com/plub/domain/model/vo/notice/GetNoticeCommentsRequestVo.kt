package com.plub.domain.model.vo.notice

import com.plub.domain.model.DomainModel

data class GetNoticeCommentsRequestVo(
    val plubbingId: Int,
    val noticeId: Int,
    val cursorId: Int
) : DomainModel