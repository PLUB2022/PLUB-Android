package com.plub.data.mapper

import com.plub.data.base.Mapper
import com.plub.data.dto.board.CommentCreateRequest
import com.plub.domain.model.vo.board.CommentCreateRequestVo

object CommentCreateRequestMapper: Mapper.RequestMapper<CommentCreateRequest, CommentCreateRequestVo> {

    override fun mapModelToDto(type: CommentCreateRequestVo): CommentCreateRequest {
        return type.run {
            CommentCreateRequest(
                content = content,
                parentCommentId = parentCommentId
            )
        }
    }
}