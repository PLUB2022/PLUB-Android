package com.plub.data.dto.hobby

import com.google.gson.annotations.SerializedName
import com.plub.data.base.DataDto

data class HobbyResponse(
    @SerializedName("id")
    val id: Int = -1,
    @SerializedName("name")
    val name: String = "",
    @SerializedName("icon")
    val icon: String = "",
    @SerializedName("subCategories")
    val subCategories: List<SubHobbyResponse> = emptyList()
):DataDto