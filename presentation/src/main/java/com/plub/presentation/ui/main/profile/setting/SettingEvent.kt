package com.plub.presentation.ui.main.profile.setting

import com.plub.presentation.ui.Event

sealed class SettingEvent : Event {
    object GoToBack : SettingEvent()
    object GoToNotice : SettingEvent()
    object GoToEmail : SettingEvent()
    object GoToFAQ : SettingEvent()
    object GoToLogin : SettingEvent()
    object ShowLogoutDialog : SettingEvent()
    object ShowInactivationDialog : SettingEvent()
    object ShowRevokeDialog : SettingEvent()
    object GoToServicePolicesPage : SettingEvent()
    object GoToPersonalPolicesPage : SettingEvent()
}
