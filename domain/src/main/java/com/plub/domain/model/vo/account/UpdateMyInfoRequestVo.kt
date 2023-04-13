package com.plub.domain.model.vo.account

import com.plub.domain.model.DomainModel

data class UpdateMyInfoRequestVo(
    val nickname : String,
    val introduce : String,
    val profileImageUrl : String?,
) : DomainModel
