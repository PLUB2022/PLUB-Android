package com.plub.domain.model.vo.home.recruitdetailvo.host

import com.plub.domain.model.DomainModel
import com.plub.domain.model.vo.home.recruitdetailvo.host.AccountsVo

data class HostApplicantsResponseVo(
    val appliedAccounts : List<AccountsVo> = emptyList()
):DomainModel