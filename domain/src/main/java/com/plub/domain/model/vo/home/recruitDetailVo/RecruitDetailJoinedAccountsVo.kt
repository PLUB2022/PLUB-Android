package com.plub.domain.model.vo.home.recruitDetailVo

import com.plub.domain.model.DomainModel

data class RecruitDetailJoinedAccountsVo(
    val accountId : Int = 0,
    val profileImage: String? = "",
    val nickname : String = ""
) : DomainModel