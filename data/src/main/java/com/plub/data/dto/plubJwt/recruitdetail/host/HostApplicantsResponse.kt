package com.plub.data.dto.plubJwt.recruitdetail.host

import com.plub.data.base.DataDto

data class HostApplicantsResponse(
    val appliedAccounts : List<Accounts>
) : DataDto
