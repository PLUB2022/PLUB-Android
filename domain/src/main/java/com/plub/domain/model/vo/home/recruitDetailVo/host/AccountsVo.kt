package com.plub.domain.model.vo.home.recruitDetailVo.host

import com.plub.domain.model.DomainModel

data class AccountsVo (
    val accountId : Int = -1,
    val accountName : String = "",
    val profileImage : String? = "",
    val createdAt : String = "",
    val answers : List<AnswersVo> = emptyList()
):DomainModel