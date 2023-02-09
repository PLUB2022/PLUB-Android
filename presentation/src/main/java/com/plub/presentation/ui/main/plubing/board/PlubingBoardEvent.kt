package com.plub.presentation.ui.main.plubing.board

import com.plub.presentation.ui.Event

sealed class PlubingBoardEvent : Event {
    object NotifyClipBoardChanged : PlubingBoardEvent()
}
