package com.plub.domain.model.vo.notice

import com.plub.domain.model.DomainModel

data class NoticeCommentEditRequestVo(
    val plubingId: Int,
    val noticeId: Int,
    val commentId: Int,
    val content: String,
) : DomainModel