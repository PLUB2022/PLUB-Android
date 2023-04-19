package com.plub.domain.model.vo.report

import com.plub.domain.model.DomainModel

data class CreateReportRequestVo(
    val reportType : String,
    val reportTarget : String,
    val reportTargetId : Int,
    val reportReason : String,
    val plubbingId : Int,
) : DomainModel
