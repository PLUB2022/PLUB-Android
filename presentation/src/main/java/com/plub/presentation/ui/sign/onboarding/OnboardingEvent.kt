package com.plub.presentation.ui.sign.onboarding

import com.plub.presentation.ui.Event

sealed class OnboardingEvent : Event {
    object GoToLoginFragment: OnboardingEvent()
    object NavigationPopEvent: OnboardingEvent()
}