package com.plub.domain.model.vo.home.recruitdetailvo

import com.plub.domain.model.DomainModel

data class RecruitDetailJoinedAccountsListVo(
    val accountId : Int = 0,
    val profileImage: String = "",
    val nickname : String = ""
) : DomainModel