package com.plub.domain.model.vo.notice

import com.plub.domain.model.DomainModel

data class NoticeVo(
    val noticeId: Int = -1,
    val title: String = "",
    val content: String = "",
    val createAt: String = "",
    val isHost: Boolean = false
) : DomainModel