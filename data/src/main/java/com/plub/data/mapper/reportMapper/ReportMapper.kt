package com.plub.data.mapper.reportMapper

import com.plub.data.base.Mapper
import com.plub.data.dto.report.ReportListResponse
import com.plub.domain.model.vo.report.ReportVo

object ReportMapper: Mapper.ResponseMapper<ReportListResponse, ReportVo> {
    override fun mapDtoToModel(type: ReportListResponse?): ReportVo {
        return type?.run {
            ReportVo(
                reportList = reportList.map {
                    ReportItemMapper.mapDtoToModel(it)
                }
            )
        }?: ReportVo()
    }
}