package com.plub.presentation.ui.main.report

import com.plub.domain.model.vo.report.ReportItemVo
import com.plub.presentation.ui.PageState

data class ReportState(
    val reportList : List<ReportItemVo> = emptyList()
) : PageState
