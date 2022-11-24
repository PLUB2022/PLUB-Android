package com.plub.domain.model.vo.home

import com.plub.domain.base.DomainModel

data class HomePostResponseVo(
    val authCode: String? = ""
) : DomainModel()
