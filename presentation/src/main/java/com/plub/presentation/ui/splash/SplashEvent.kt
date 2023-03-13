package com.plub.presentation.ui.splash

import com.plub.presentation.ui.Event

sealed class SplashEvent : Event {
    object GoToMain : SplashEvent()
    object GoToSignUp : SplashEvent()
}
