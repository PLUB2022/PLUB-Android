package com.plub.data.dto.recruitDetail.host

import com.google.gson.annotations.SerializedName
import com.plub.data.base.DataDto

data class HostApplicantsListResponse(
    @SerializedName("appliedAccounts")
    val appliedAccounts : List<AccountDataResponse> = emptyList()
) : DataDto