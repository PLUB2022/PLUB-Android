package com.plub.domain.model.vo.board

import com.plub.domain.model.DomainModel
import com.plub.domain.model.enums.PlubingCommentType

data class BoardCommentListVo(
    val totalPages: Int = -1,
    val totalElements: Int = -1,
    val content: List<BoardCommentVo> = emptyList(),
    val last: Boolean = false,
) : DomainModel