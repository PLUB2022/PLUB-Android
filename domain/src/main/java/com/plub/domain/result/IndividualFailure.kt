package com.plub.domain.result

sealed class IndividualFailure: StateResult.Fail() {
    object Undefined : IndividualFailure()
    object Invalided : IndividualFailure()
}
