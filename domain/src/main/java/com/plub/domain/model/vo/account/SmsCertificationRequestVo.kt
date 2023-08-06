package com.plub.domain.model.vo.account

import com.plub.domain.model.DomainModel

data class SmsCertificationRequestVo(
    val phone : String = "",
    val certificationNum : Int = 0,
) : DomainModel
