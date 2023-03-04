package com.plub.data.mapper

import com.plub.data.base.Mapper
import com.plub.data.dto.board.BoardCommentResponse
import com.plub.domain.model.enums.PlubingCommentType
import com.plub.domain.model.vo.board.BoardCommentVo

object BoardCommentResponseMapper : Mapper.ResponseMapper<BoardCommentResponse, BoardCommentVo> {

    override fun mapDtoToModel(type: BoardCommentResponse?): BoardCommentVo {
        return type?.run {
            BoardCommentVo(
                commentType = PlubingCommentType.typeOf(commentType),
                commentId = commentId,
                parentCommentId = parentCommentId ?: -1,
                parentCommentNickname = parentCommentNickname ?: "",
                content = content,
                profileImage = profileImage,
                nickname = nickname,
                createAt = createdAt,
                isCommentAuthor = isCommentAuthor,
                isFeedAuthor = isFeedAuthor,
            )
        }?: BoardCommentVo()
    }
}