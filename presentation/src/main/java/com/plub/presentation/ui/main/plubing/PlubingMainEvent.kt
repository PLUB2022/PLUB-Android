package com.plub.presentation.ui.main.plubing

import com.plub.presentation.ui.Event

sealed class PlubingMainEvent : Event {
    data class GoToWriteBoard(val id: Int, val name: String) : PlubingMainEvent()
    data class GoToWriteTodo(val id: Int, val name: String) : PlubingMainEvent()
}
