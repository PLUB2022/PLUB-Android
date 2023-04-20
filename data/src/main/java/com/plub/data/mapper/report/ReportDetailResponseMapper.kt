package com.plub.data.mapper.report

import com.plub.data.base.Mapper
import com.plub.data.dto.report.ReportDetailResponse
import com.plub.domain.model.vo.report.ReportDetailVo

object ReportDetailResponseMapper : Mapper.ResponseMapper<ReportDetailResponse, ReportDetailVo> {
    override fun mapDtoToModel(type: ReportDetailResponse?): ReportDetailVo {
        return type?.run {
            ReportDetailVo(
                reportTitle = reportTitle,
                reportContent = reportContent,
                reportedAt = reportedAt
            )
        } ?: ReportDetailVo()
    }
}