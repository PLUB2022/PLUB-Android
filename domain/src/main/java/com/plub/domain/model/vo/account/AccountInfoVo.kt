package com.plub.domain.model.vo.account

import com.plub.domain.model.DomainModel

data class AccountInfoVo(
    val userId: Int = -1,
    val nickname: String = "",
    val profileImage: String = ""
) : DomainModel