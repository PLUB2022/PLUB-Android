package com.plub.domain.model.vo.home.interestRegisterVo

import com.plub.domain.model.DomainModel

data class RegisterInterestResponseVo(
    val accountId : String = "",
    val subCategories : List<Int> = emptyList()
) : DomainModel