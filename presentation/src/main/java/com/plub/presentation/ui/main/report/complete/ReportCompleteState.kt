package com.plub.presentation.ui.main.report.complete

import com.plub.presentation.ui.PageState
import kotlinx.coroutines.flow.StateFlow

data class ReportCompleteState(
    val reportText : StateFlow<String>
) : PageState