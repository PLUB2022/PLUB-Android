package com.plub.domain.model.vo.home.recruitDetailVo.host

import com.plub.domain.model.DomainModel

data class HostApplicantsResponseVo(
    val appliedAccounts : List<AccountsVo> = emptyList()
):DomainModel