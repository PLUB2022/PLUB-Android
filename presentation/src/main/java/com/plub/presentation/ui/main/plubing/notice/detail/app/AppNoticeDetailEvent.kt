package com.plub.presentation.ui.main.plubing.notice.detail.app

import com.plub.presentation.ui.Event

sealed class AppNoticeDetailEvent : Event {
    object GoToBack : AppNoticeDetailEvent()
}