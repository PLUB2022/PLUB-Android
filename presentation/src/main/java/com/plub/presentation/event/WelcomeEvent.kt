package com.plub.presentation.event

sealed class WelcomeEvent : Event {
    object GoToMain : WelcomeEvent()
}