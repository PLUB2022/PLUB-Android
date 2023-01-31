package com.plub.domain.model.vo.home.recruitdetailvo

import com.plub.domain.base.DomainModel

data class RecruitDetailJoinedAccountsListVo(
    val accountId : Int = 0,
    val profileImage: String? = ""
) : DomainModel()