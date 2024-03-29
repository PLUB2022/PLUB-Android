package com.plub.data.dto.registerHobbies

import com.google.gson.annotations.SerializedName
import com.plub.data.base.DataDto

data class RegisterHobbiesResponse(
    @SerializedName("subCategories")
    val subCategories : List<Int> = emptyList()
):DataDto