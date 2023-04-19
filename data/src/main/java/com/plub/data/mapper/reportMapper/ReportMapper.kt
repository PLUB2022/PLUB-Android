package com.plub.data.mapper.reportMapper

import com.plub.data.base.Mapper
import com.plub.data.dto.report.ReportResponse
import com.plub.domain.model.vo.report.ReportVo

object ReportMapper: Mapper.ResponseMapper<ReportResponse, ReportVo> {
    override fun mapDtoToModel(type: ReportResponse?): ReportVo {
        return type?.run {
            ReportVo(
                reportList = reportList.map {
                    ReportItemMapper.mapDtoToModel(it)
                }
            )
        }?: ReportVo()
    }
}