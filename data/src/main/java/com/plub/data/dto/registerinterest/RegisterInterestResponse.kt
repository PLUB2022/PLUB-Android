package com.plub.data.dto.registerinterest

import com.google.gson.annotations.SerializedName
import com.plub.data.base.DataDto

data class RegisterInterestResponse(
    @SerializedName("accountId")
    val accountId : String = "",
    @SerializedName("subCategories")
    val subCategories : List<Int> = emptyList()
):DataDto