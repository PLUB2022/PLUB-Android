package com.plub.domain.model.vo.notice

import com.plub.domain.model.DomainModel

data class PostNoticeWriteRequestVo(
    val plubbingId: Int,
    val noticeId: Int = -1,
    val title: String,
    val content: String,
) : DomainModel