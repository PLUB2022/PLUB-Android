package com.plub.presentation.ui.main.report.detail

import com.plub.presentation.ui.Event

sealed class ReportDetailEvent : Event{
    object ShowSpinner : ReportDetailEvent()
    object GoneSpinner : ReportDetailEvent()
    object BorderBlack : ReportDetailEvent()
    object BorderDefault : ReportDetailEvent()
    object GoToBack : ReportDetailEvent()
    data class GoToComplete(val nowText : String) : ReportDetailEvent()
}
