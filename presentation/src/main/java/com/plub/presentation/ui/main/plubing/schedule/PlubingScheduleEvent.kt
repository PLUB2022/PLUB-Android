package com.plub.presentation.ui.main.plubing.schedule

import com.plub.presentation.ui.Event

sealed class PlubingScheduleEvent : Event {
    data class GoToAddSchedule(val id: Int): PlubingScheduleEvent()
}
