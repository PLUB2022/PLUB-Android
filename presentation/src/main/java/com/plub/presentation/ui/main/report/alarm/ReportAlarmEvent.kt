package com.plub.presentation.ui.main.report.alarm

import com.plub.presentation.ui.Event

sealed class ReportAlarmEvent : Event{
    object GoToBack : ReportAlarmEvent()
}
