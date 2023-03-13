package com.plub.domain.model.vo.board

import com.plub.domain.model.DomainModel
import com.plub.domain.model.enums.PlubingBoardType
import com.plub.domain.model.vo.plub.PlubCardVo

data class PlubingBoardListVo(
    val totalPages: Int = -1,
    val totalElements: Int = -1,
    val content: List<PlubingBoardVo> = emptyList(),
    val last: Boolean = false,
) : DomainModel