package com.plub.domain.model

import com.plub.domain.base.DomainModel

data class SampleLogin(
    val login: String,
    val register: String
):DomainModel()
