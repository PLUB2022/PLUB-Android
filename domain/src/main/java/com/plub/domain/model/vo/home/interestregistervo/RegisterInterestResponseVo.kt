package com.plub.domain.model.vo.home.interestregistervo

import com.plub.domain.base.DomainModel

data class RegisterInterestResponseVo(
    val accountId : String,
    val subCategories : List<Int>
) : DomainModel()
