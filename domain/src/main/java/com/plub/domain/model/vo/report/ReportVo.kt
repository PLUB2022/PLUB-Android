package com.plub.domain.model.vo.report

import com.plub.domain.model.DomainModel

data class ReportVo(
    val reportList : List<ReportItemVo> = emptyList()
) : DomainModel
