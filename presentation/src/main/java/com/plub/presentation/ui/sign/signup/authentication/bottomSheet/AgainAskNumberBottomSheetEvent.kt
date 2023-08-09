package com.plub.presentation.ui.sign.signup.authentication.bottomSheet

import com.plub.presentation.ui.Event

sealed class AgainAskNumberBottomSheetEvent : Event {
    object ClickSendButton : AgainAskNumberBottomSheetEvent()
    object ClickCloseButton : AgainAskNumberBottomSheetEvent()
}