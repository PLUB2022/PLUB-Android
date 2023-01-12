package com.plub.data.dto.hobby

import com.google.gson.annotations.SerializedName
import com.plub.data.base.DataDto

data class AllHobbiesResponse(
    @SerializedName("categories")
    val categories:List<HobbyResponse> = emptyList()
):DataDto