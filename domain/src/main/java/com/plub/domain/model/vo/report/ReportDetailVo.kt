package com.plub.domain.model.vo.report

import com.plub.domain.model.DomainModel

data class ReportDetailVo(
    val reportTitle : String = "",
    val reportContent : String = "",
    val reportedAt : String = ""
):DomainModel
