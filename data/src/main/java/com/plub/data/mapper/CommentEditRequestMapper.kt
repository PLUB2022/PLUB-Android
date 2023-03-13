package com.plub.data.mapper

import com.plub.data.base.Mapper
import com.plub.data.dto.board.CommentEditRequest
import com.plub.domain.model.vo.board.CommentEditRequestVo

object CommentEditRequestMapper: Mapper.RequestMapper<CommentEditRequest, CommentEditRequestVo> {

    override fun mapModelToDto(type: CommentEditRequestVo): CommentEditRequest {
        return type.run {
            CommentEditRequest(
                content = content
            )
        }
    }
}