package com.plub.data.mapper.notice

import com.plub.data.base.Mapper
import com.plub.data.dto.notice.AppNoticeDataResponse
import com.plub.domain.model.vo.notice.NoticeListVo

object AppNoticeListResponseMapper : Mapper.ResponseMapper<AppNoticeDataResponse, NoticeListVo> {
    private val appNoticeResponseMapper = AppNoticeResponseMapper

    override fun mapDtoToModel(type: AppNoticeDataResponse?): NoticeListVo {
        return type?.run {
            NoticeListVo(
                last = announcementList.last,
                content = announcementList.content.map {
                    appNoticeResponseMapper.mapDtoToModel(it)
                }
            )
        } ?: NoticeListVo()
    }
}