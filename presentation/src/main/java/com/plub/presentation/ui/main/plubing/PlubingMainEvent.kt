package com.plub.presentation.ui.main.plubing

import com.plub.presentation.ui.Event

sealed class PlubingMainEvent : Event {
    object GoToWriteBoard : PlubingMainEvent()
    object GoToPlannerTodo : PlubingMainEvent()
    object GoToNotice : PlubingMainEvent()
}
