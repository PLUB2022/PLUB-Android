package com.plub.domain.model.vo.home

import com.plub.domain.base.DomainModel

data class HomePostRequestVo(
    val authCode: String,
    val isLoginSuccess : Boolean
) : DomainModel()
