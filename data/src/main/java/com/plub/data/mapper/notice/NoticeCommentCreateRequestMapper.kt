package com.plub.data.mapper.notice

import com.plub.data.base.Mapper
import com.plub.data.dto.board.CommentCreateRequest
import com.plub.domain.model.vo.notice.NoticeCommentCreateRequestVo

object NoticeCommentCreateRequestMapper: Mapper.RequestMapper<CommentCreateRequest, NoticeCommentCreateRequestVo> {

    override fun mapModelToDto(type: NoticeCommentCreateRequestVo): CommentCreateRequest {
        return type.run {
            CommentCreateRequest(
                content = content,
                parentCommentId = parentCommentId
            )
        }
    }
}