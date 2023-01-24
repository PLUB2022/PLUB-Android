package com.plub.data.dto.plubJwt.registerinterest

import com.plub.data.base.DataDto

data class RegisterInterestResponse(
    val accountId : String,
    val subCategories : List<Int>
):DataDto
