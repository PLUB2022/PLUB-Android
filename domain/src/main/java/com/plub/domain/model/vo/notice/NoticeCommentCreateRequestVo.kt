package com.plub.domain.model.vo.notice

import com.plub.domain.model.DomainModel

data class NoticeCommentCreateRequestVo(
    val plubbingId: Int,
    val noticeId: Int,
    val content: String,
    val parentCommentId:Int? = null
) : DomainModel