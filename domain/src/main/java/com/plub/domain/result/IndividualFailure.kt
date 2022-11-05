package com.plub.domain.result

sealed class IndividualFailure: Failure() {
    object Undefined : IndividualFailure()
    object Invalided : IndividualFailure()
}
