package com.plub.domain.model.vo.notice

import com.plub.domain.model.DomainModel

data class NoticeListVo(
    val content: List<NoticeVo> = emptyList(),
    val last: Boolean = false,
) : DomainModel