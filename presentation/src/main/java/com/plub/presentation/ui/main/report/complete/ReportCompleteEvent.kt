package com.plub.presentation.ui.main.report.complete

import com.plub.presentation.ui.Event

sealed class ReportCompleteEvent : Event {
    object GoHome : ReportCompleteEvent()
    object GoBack : ReportCompleteEvent()
}