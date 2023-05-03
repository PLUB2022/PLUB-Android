package com.plub.data.mapper.notice

import com.plub.data.base.Mapper
import com.plub.data.dto.notice.NoticeWriteRequest
import com.plub.domain.model.vo.notice.PostNoticeWriteRequestVo

object NoticeWriteRequestMapper: Mapper.RequestMapper<NoticeWriteRequest, PostNoticeWriteRequestVo> {
    override fun mapModelToDto(type: PostNoticeWriteRequestVo): NoticeWriteRequest {
        return type.run {
            NoticeWriteRequest(
                title = title,
                content = content
            )
        }
    }
}