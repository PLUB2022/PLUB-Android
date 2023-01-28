package com.plub.presentation.event

sealed class CategoryChoiceEvent : Event {
    object GoToBack : CategoryChoiceEvent()
}