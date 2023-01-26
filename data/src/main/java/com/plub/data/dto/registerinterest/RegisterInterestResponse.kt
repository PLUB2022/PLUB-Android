package com.plub.data.dto.registerinterest

import com.plub.data.base.DataDto

data class RegisterInterestResponse(
    val accountId : String = "",
    val subCategories : List<Int> = emptyList()
):DataDto