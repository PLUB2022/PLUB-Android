package com.plub.domain.model.vo.plub

import com.plub.domain.model.DomainModel

data class PlubCardListVo(
    val totalPages: Int = -1,
    val totalElements: Int = -1,
    val content: List<PlubCardVo> = emptyList(),
    val last: Boolean = false,
): DomainModel
