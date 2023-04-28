package com.plub.domain.model.vo.home.interestRegisterVo

import com.plub.domain.model.DomainModel

data class RegisterInterestResponseVo(
    val subCategories : List<Int> = emptyList()
) : DomainModel