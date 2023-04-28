package com.plub.presentation.ui.main.report.complete

import com.plub.presentation.ui.Event

sealed class ReportCompleteEvent : Event {
    object GoBack : ReportCompleteEvent()
}