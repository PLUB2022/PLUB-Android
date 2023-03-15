package com.plub.presentation.ui.main.report.detail

import com.plub.domain.model.vo.report.ReportItemVo
import com.plub.presentation.ui.PageState

data class ReportDetailState(
    val reportList : List<ReportItemVo> = emptyList(),
    val nowText : String = ""
) : PageState
