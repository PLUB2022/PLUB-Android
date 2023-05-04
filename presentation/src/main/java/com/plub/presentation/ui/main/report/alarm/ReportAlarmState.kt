package com.plub.presentation.ui.main.report.alarm

import com.plub.presentation.ui.PageState
import kotlinx.coroutines.flow.StateFlow

data class ReportAlarmState(
    val title : StateFlow<String>,
    val date : StateFlow<String>,
    val content : StateFlow<String>
) : PageState
