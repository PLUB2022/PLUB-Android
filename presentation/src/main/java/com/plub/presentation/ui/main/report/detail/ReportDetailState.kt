package com.plub.presentation.ui.main.report.detail

import com.plub.domain.model.vo.report.ReportItemVo
import com.plub.presentation.ui.PageState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

data class ReportDetailState(
    val reportList : StateFlow<List<ReportItemVo>>,
    val nowText : StateFlow<String>,
    var reportContent : MutableStateFlow<String>,
) : PageState
