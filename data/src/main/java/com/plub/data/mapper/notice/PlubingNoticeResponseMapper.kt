package com.plub.data.mapper.notice

import com.plub.data.base.Mapper
import com.plub.data.dto.notice.AppNoticeResponse
import com.plub.data.dto.notice.PlubingNoticeResponse
import com.plub.domain.model.enums.NoticeType
import com.plub.domain.model.vo.notice.NoticeVo

object PlubingNoticeResponseMapper : Mapper.ResponseMapper<PlubingNoticeResponse, NoticeVo> {

    override fun mapDtoToModel(type: PlubingNoticeResponse?): NoticeVo {
        return type?.run {
            NoticeVo(
                noticeType = NoticeType.PLUBING,
                noticeId = noticeId,
                title = title,
                content = content,
                createAt = createdAt,
                likeCount = likeCount ?: -1,
                commentCount = commentCount ?: -1,
                isHost = isHost,
            )
        }?: NoticeVo()
    }
}