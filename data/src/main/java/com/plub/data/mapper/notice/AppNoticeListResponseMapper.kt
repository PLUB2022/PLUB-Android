package com.plub.data.mapper.notice

import com.plub.data.base.Mapper
import com.plub.data.dto.notice.AppNoticeListResponse
import com.plub.domain.model.vo.notice.NoticeListVo

object AppNoticeListResponseMapper : Mapper.ResponseMapper<AppNoticeListResponse, NoticeListVo> {
    private val appNoticeResponseMapper = AppNoticeResponseMapper

    override fun mapDtoToModel(type: AppNoticeListResponse?): NoticeListVo {
        return type?.run {
            NoticeListVo(
                last = last,
                content = content.map {
                    appNoticeResponseMapper.mapDtoToModel(it)
                }
            )
        } ?: NoticeListVo()
    }
}