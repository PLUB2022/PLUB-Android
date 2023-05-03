package com.plub.data.dto.account

import com.google.gson.annotations.SerializedName
import com.plub.data.base.DataDto

data class AccountInfosResponse(
    @SerializedName("accountInfo")
    val accountInfo: List<AccountInfoResponse> = emptyList()
): DataDto