package com.plub.domain.model.vo.board

import com.plub.domain.model.DomainModel

data class PostCommentRequestVo(
    val plubingId: Int,
    val feedId: Int,
    val content: String,
    val parentCommentId:Int? = null
) : DomainModel