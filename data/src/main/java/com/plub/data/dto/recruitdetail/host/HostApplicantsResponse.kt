package com.plub.data.dto.recruitdetail.host

import com.plub.data.base.DataDto

data class HostApplicantsResponse(
    val appliedAccounts : List<Accounts>
) : DataDto