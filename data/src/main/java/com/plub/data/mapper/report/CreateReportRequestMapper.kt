package com.plub.data.mapper.report

import com.plub.data.base.Mapper
import com.plub.data.dto.report.CreateReportRequest
import com.plub.domain.model.vo.report.CreateReportRequestVo

object CreateReportRequestMapper : Mapper.RequestMapper<CreateReportRequest, CreateReportRequestVo> {
    override fun mapModelToDto(type: CreateReportRequestVo): CreateReportRequest {
        return type?.run {
            CreateReportRequest(
                reportType = reportType,
                reportTarget = reportTarget,
                reportReason = reportReason,
                reportTargetId = reportTargetId,
                plubbingId = plubbingId
            )
        } ?: CreateReportRequest()
    }
}