package com.plub.domain.model.vo.board

import com.plub.domain.model.DomainModel

data class CommentEditRequestVo(
    val plubingId: Int,
    val feedId: Int,
    val commentId: Int,
    val content: String,
) : DomainModel