package com.plub.domain.model.vo.report

import com.plub.domain.model.DomainModel
import com.plub.domain.model.enums.ReportBackgroundType
import com.plub.domain.model.enums.ReportReasonType

data class ReportItemVo(
    val reportTitle : String = "",
    val reportBackgroundType : ReportBackgroundType = ReportBackgroundType.SPINNER,
    val reportType : ReportReasonType = ReportReasonType.BAD_WORDS
) : DomainModel
