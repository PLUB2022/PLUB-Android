package com.plub.data.dto.recruitdetail.host

import com.google.gson.annotations.SerializedName
import com.plub.data.base.DataDto
import com.plub.data.dto.recruitdetail.host.AccountDataResponse

data class HostApplicantsListResponse(
    @SerializedName("appliedAccounts")
    val appliedAccounts : List<AccountDataResponse> = emptyList()
) : DataDto