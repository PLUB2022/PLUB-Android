package com.plub.data.mapper.notice

import com.plub.data.base.Mapper
import com.plub.data.dto.notice.PlubingNoticeDataResponse
import com.plub.data.dto.notice.PlubingNoticeListResponse
import com.plub.domain.model.vo.notice.NoticeListVo

object PlubingNoticeListResponseMapper : Mapper.ResponseMapper<PlubingNoticeDataResponse, NoticeListVo> {
    private val plubingNoticeResponseMapper = PlubingNoticeResponseMapper

    override fun mapDtoToModel(type: PlubingNoticeDataResponse?): NoticeListVo {
        return type?.run {
            NoticeListVo(
                last = noticeList.last,
                content = noticeList.content.map {
                    plubingNoticeResponseMapper.mapDtoToModel(it)
                }
            )
        } ?: NoticeListVo()
    }
}