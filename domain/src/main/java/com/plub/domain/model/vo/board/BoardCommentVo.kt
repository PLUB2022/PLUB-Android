package com.plub.domain.model.vo.board

import com.plub.domain.model.DomainModel
import com.plub.domain.model.enums.PlubingCommentType

data class BoardCommentVo(
    val commentType: PlubingCommentType = PlubingCommentType.COMMENT,
    val commentId: Int = -1,
    val parentCommentId: Int = -1,
    val parentCommentNickname: String = "",
    val content: String = "",
    val profileImage: String = "",
    val nickname: String = "",
    val createAt: String = "",
    val isCommentAuthor: Boolean = false,
    val isFeedAuthor: Boolean = false,
) : DomainModel