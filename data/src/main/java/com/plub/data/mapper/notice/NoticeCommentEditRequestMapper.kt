package com.plub.data.mapper.notice

import com.plub.data.base.Mapper
import com.plub.data.dto.board.CommentEditRequest
import com.plub.domain.model.vo.notice.NoticeCommentEditRequestVo

object NoticeCommentEditRequestMapper: Mapper.RequestMapper<CommentEditRequest, NoticeCommentEditRequestVo> {

    override fun mapModelToDto(type: NoticeCommentEditRequestVo): CommentEditRequest {
        return type.run {
            CommentEditRequest(
                content = content
            )
        }
    }
}