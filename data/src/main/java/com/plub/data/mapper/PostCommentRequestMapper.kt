package com.plub.data.mapper

import com.plub.data.base.Mapper
import com.plub.data.dto.board.PostCommentRequest
import com.plub.domain.model.vo.board.PostCommentRequestVo

object PostCommentRequestMapper: Mapper.RequestMapper<PostCommentRequest, PostCommentRequestVo> {

    override fun mapModelToDto(type: PostCommentRequestVo): PostCommentRequest {
        return type.run {
            PostCommentRequest(
                content = content,
                parentCommentId = parentCommentId
            )
        }
    }
}