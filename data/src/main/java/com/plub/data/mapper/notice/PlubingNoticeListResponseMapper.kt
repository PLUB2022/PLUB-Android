package com.plub.data.mapper.notice

import com.plub.data.base.Mapper
import com.plub.data.dto.notice.PlubingNoticeListResponse
import com.plub.domain.model.vo.notice.NoticeListVo

object PlubingNoticeListResponseMapper : Mapper.ResponseMapper<PlubingNoticeListResponse, NoticeListVo> {
    private val plubingNoticeResponseMapper = PlubingNoticeResponseMapper

    override fun mapDtoToModel(type: PlubingNoticeListResponse?): NoticeListVo {
        return type?.run {
            NoticeListVo(
                last = last,
                content = content.map {
                    plubingNoticeResponseMapper.mapDtoToModel(it)
                }
            )
        } ?: NoticeListVo()
    }
}