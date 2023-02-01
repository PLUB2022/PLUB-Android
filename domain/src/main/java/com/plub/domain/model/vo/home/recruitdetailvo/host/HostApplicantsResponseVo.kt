package com.plub.domain.model.vo.home.recruitdetailvo.host

import com.plub.domain.model.DomainModel

data class HostApplicantsResponseVo(
    val appliedAccounts : List<AccountsVo> = emptyList()
):DomainModel