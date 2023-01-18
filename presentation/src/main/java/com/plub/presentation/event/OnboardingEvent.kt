package com.plub.presentation.event

sealed class OnboardingEvent : Event {
    object GoToLoginFragment: OnboardingEvent()
    object NavigationPopEvent: OnboardingEvent()
}