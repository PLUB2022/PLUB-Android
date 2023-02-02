package com.plub.presentation.ui.sign.welcome

import com.plub.presentation.ui.Event

sealed class WelcomeEvent : Event {
    object GoToMain : WelcomeEvent()
}