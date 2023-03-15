package com.plub.domain.model.vo.report

import com.plub.domain.model.DomainModel

data class ReportItemVo(
    val reportTitle : String = "",
    val reportType : Int = 0
) : DomainModel
