package com.plub.domain.error

sealed class IndividualError : UiError() {
    object Undefined : IndividualError()
}