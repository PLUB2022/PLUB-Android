package com.plub.presentation.ui.main.report

import com.plub.domain.model.vo.report.ReportItemVo
import com.plub.presentation.ui.PageState
import kotlinx.coroutines.flow.StateFlow

data class ReportState(
    val reportTitle : StateFlow<String>,
    val reportList : StateFlow<List<ReportItemVo>>
) : PageState
