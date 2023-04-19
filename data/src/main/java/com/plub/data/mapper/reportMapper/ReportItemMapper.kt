package com.plub.data.mapper.reportMapper

import com.plub.data.base.Mapper
import com.plub.data.dto.report.ReportItemResponse
import com.plub.domain.model.vo.report.ReportItemVo

object ReportItemMapper: Mapper.ResponseMapper<ReportItemResponse, ReportItemVo> {
    override fun mapDtoToModel(type: ReportItemResponse?): ReportItemVo {
        return type?.run {
            ReportItemVo(
                reportTitle = detailContent,
                reportType = reportType
            )
        }?: ReportItemVo()
    }
}