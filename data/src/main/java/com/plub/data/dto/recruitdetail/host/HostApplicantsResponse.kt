package com.plub.data.dto.recruitdetail.host

import com.google.gson.annotations.SerializedName
import com.plub.data.base.DataDto

data class HostApplicantsResponse(
    @SerializedName("appliedAccounts")
    val appliedAccounts : List<Accounts> = emptyList()
) : DataDto