package com.plub.presentation.ui.onboarding

import com.plub.presentation.ui.Event

sealed class OnboardingEvent : Event {
    object GoToLoginFragment: OnboardingEvent()
    object NavigationPopEvent: OnboardingEvent()
}