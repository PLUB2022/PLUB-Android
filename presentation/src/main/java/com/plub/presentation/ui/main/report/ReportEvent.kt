package com.plub.presentation.ui.main.report

import com.plub.domain.model.enums.ReportReasonType
import com.plub.presentation.ui.Event

sealed class ReportEvent : Event{
    object GoToBack: ReportEvent()
    data class GoToReport(val type : ReportReasonType) : ReportEvent()
}
