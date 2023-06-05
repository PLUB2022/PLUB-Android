package com.plub.data.mapper.notice

import com.plub.data.base.Mapper
import com.plub.data.dto.notice.AppNoticeResponse
import com.plub.domain.model.enums.NoticeType
import com.plub.domain.model.vo.notice.NoticeVo

object AppNoticeResponseMapper : Mapper.ResponseMapper<AppNoticeResponse, NoticeVo> {

    override fun mapDtoToModel(type: AppNoticeResponse?): NoticeVo {
        return type?.run {
            NoticeVo(
                noticeType = NoticeType.APP,
                noticeId = announcementId,
                title = title,
                content = content,
                createAt = createdAt,
                isHost = false,
            )
        }?: NoticeVo()
    }
}