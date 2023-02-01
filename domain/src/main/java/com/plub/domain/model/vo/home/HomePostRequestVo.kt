package com.plub.domain.model.vo.home

import com.plub.domain.model.DomainModel

data class HomePostRequestVo(
    val authCode: String,
    val isLoginSuccess : Boolean
) : DomainModel
