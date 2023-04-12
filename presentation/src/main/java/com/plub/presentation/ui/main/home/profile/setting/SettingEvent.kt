package com.plub.presentation.ui.main.home.profile.setting

import com.plub.presentation.ui.Event

sealed class SettingEvent : Event {
    object GoToBack : SettingEvent()
    object GoToNotice : SettingEvent()
    object GoToEmail : SettingEvent()
    object GoToFAQ : SettingEvent()
}
